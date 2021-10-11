package com.skl.foodrecipeapp.presentation.recipelist.searchrecipe

import androidx.lifecycle.ViewModel
import com.skl.foodrecipeapp.common.domain.interactors.recipelist.SearchRecipesUseCase
import com.skl.foodrecipeapp.common.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase
): ViewModel() {

    private val query: MutableStateFlow<String> = MutableStateFlow("")

    private var cuisine: List<String> = emptyList()
    private var diet: List<String> = emptyList()
    private var intolerance: List<String> = emptyList()


    @ExperimentalCoroutinesApi
    val recipes: Flow<List<Recipe>> = query.flatMapLatest {
        searchRecipesUseCase.invoke(it, cuisine, diet, intolerance)
    }

    fun setQuery(newQuery: String) {
        query.value = newQuery
    }

    fun setCuisine(newCuisine: List<String>){
        cuisine = newCuisine
    }
    fun setDiet(newDiet: List<String>){
        diet = newDiet
    }
    fun setIntolerance(newIntolerance: List<String>){
        intolerance = newIntolerance
    }
}