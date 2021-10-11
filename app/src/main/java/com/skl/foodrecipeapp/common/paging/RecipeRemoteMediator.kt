package com.skl.foodrecipeapp.common.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.skl.foodrecipeapp.common.data.datasource.api.mapper.ApiRecipeMapper
import com.skl.foodrecipeapp.common.data.datasource.api.model.ApiRecipe
import com.skl.foodrecipeapp.common.data.datasource.api.webServices.RecipeNetworkDataSource
import com.skl.foodrecipeapp.common.data.datasource.cache.RecipeAppDatabase
import com.skl.foodrecipeapp.common.data.datasource.cache.model.RemoteKey
import com.skl.foodrecipeapp.common.data.repository.RecipesRepositoryImpl
import com.skl.foodrecipeapp.common.data.repository.utils.listToString
import com.skl.foodrecipeapp.common.domain.mapper.RecipeMapper
import com.skl.foodrecipeapp.common.domain.model.Recipe
import okio.IOException
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class RecipeRemoteMediator(
    val isSearched: Boolean,
    val query: String,
    val db: RecipeAppDatabase,
    val webservice: RecipeNetworkDataSource,
    val cuisine: String,
    val diet: List<String>,
    val intolerance: List<String>,
    private val apiMapper: ApiRecipeMapper,
    private val domainMapper: RecipeMapper
) : RemoteMediator<Int, Recipe>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Recipe>): MediatorResult {

        val pageKey = getLoadKey(loadType, state)
        val page = when(pageKey){
            is MediatorResult.Success ->{
                return pageKey
            }
            else ->{
                pageKey as Int
            }
        }
        return try {

            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.

            val results = getResults()
            val isEndOfList = results?.isEmpty()
            val recipes = results?.map { apiRecipe -> apiMapper.toCacheModel(apiRecipe) }

            if(recipes != null){
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.recipeDao.deleteAllRecipes()
                        db.remoteKeyDao.deleteKeys()
                    }

                    val prevKey = if(page == STARTING_PAGE_INDEX) null else { page - 1 }
                    val nextKey = if((isEndOfList != null) && (isEndOfList == true)) null else { page + 1 }
                    val keys = recipes.map {
                        RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                    }

                    // Insert new users into database, which invalidates the
                    // current PagingData, allowing Paging to present the updates
                    // in the DB.

                    db.recipeDao.insertRecipes(recipes)
                    db.remoteKeyDao.insertOrReplace(keys)

                }
            }
            MediatorResult.Success(endOfPaginationReached = isEndOfList ?: true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getResults(): List<ApiRecipe>? {
        return if(isSearched) {
            val response = webservice.searchRecipe(query = query, cuisine = cuisine, diet = listToString(diet), intolerance = listToString(intolerance))
            response.isSuccessful.let {
                response.body()?.results
            }
        } else{
            val tags = getTag(cuisine, diet, intolerance)
            val response = webservice.getRandomRecipes(tags = tags)
            response.isSuccessful.let {
                response.body()?.results
            }
        }
    }

    private suspend fun getLoadKey(loadType: LoadType, state: PagingState<Int, Recipe>): Any{
        return when (loadType) {
            LoadType.REFRESH -> {

                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.

                val remoteKeys = getCurrentPosition(state)
                val current = remoteKeys?.nextKey?.minus(1)
                current ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstPosition(state)
                val nextKey = remoteKeys?.prevKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
                LoadType.APPEND -> {

                val remoteKeys = getLastPosition(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: MediatorResult.Success(endOfPaginationReached = false)

                // You must explicitly check if the last item is null when
                // appending, since passing null to networkService is only
                // valid for initial load. If lastItem is null it means no
                // items were loaded after the initial REFRESH and there are
                // no more items to load.
            }
        }
    }

    private suspend fun getCurrentPosition(state: PagingState<Int, Recipe>): RemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id ->
                db.remoteKeyDao.remoteKeyByQuery(id)
            }
        }
    }

    private suspend fun getLastPosition(state: PagingState<Int, Recipe>): RemoteKey? {
        return state.pages.lastOrNull(){ it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { recipe ->
                db.remoteKeyDao.remoteKeyByQuery(recipe.id)
            }
        }

    private suspend fun getFirstPosition(state: PagingState<Int, Recipe>): RemoteKey? {
        return state.pages.firstOrNull(){ it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { recipe ->
                db.remoteKeyDao.remoteKeyByQuery(recipe.id)
            }
    }

    private fun getTag(
        item: String,
        list1: List<String>,
        list2: List<String>
    ): String {
        return if(item.isEmpty() && list1.isEmpty() && list2.isEmpty()){
            ""
        }else {
            val sb = StringBuilder()
            if(item.isNotEmpty()){
                sb.append(item)
            }
            if(list1.isNotEmpty()){
                if(sb.isNotEmpty()){
                    sb.append(",")
                }
                sb.append(listToString(list1))
            }
            if (list2.isNotEmpty()){
                if(sb.isNotEmpty()){
                    sb.append(",")
                }
                sb.append(listToString(list2))
            }
            sb.toString()
        }
    }

    private fun listToString(list: List<String>): String {
        if (list.isEmpty()) {
            return ""
        }
        return list.joinToString(separator = ",")
    }
}