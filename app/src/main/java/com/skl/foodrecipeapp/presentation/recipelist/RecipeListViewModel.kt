package com.skl.foodrecipeapp.presentation.recipelist

import android.util.Log
import androidx.lifecycle.*
import com.skl.foodrecipeapp.common.domain.interactors.recipelist.GetRandomRecipes
import com.skl.foodrecipeapp.common.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@ExperimentalCoroutinesApi
@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRandomRecipes: GetRandomRecipes
): ViewModel() {

    /**
     * Temporary solution until proper solution found
     */

    private var refreshed: Boolean = true

    private var cuisine = MutableStateFlow("")
    private var _diet = MutableStateFlow<ArrayList<String>>(arrayListOf())
    private var _intolerance = MutableStateFlow<ArrayList<String>>(arrayListOf())

    val diet get() = _diet
    val intolerance get() = _intolerance

    private var _recipes: Flow<List<Recipe>> =
        combine(cuisine, _diet, _intolerance)
        { cuisine, diet_list, intolerance_list ->
            Log.d(TAG, "cuisine is: $cuisine")
            Log.d(TAG, "diet is: ${_diet.value}")
            Log.d(TAG, "intolerance is: ${_intolerance.value}")
            Triple(cuisine, diet_list, intolerance_list)
        }.flatMapLatest { (cuisine, diet_list, intolerance_list) ->
            getRandomRecipes.invoke(cuisine, diet_list.toList(), intolerance_list.toList())
    }

    val recipes get() = _recipes

    init {
        Log.d(TAG, "viewmodel has been initialized")
    }

    private fun getRecipes(cuisine: String, diet: ArrayList<String>, intolerance:ArrayList<String>){
        viewModelScope.launch {
            _recipes = if(refreshed){
                getRandomRecipes.invoke(cuisine, diet, intolerance)
            }else{
                val response_list = getRandomRecipes.invoke(cuisine, diet, intolerance)
                var new_list: ArrayList<Recipe> = arrayListOf()
                _recipes.collectLatest { recipes ->
                    new_list = ArrayList(recipes)
                    response_list.collectLatest {
                        new_list.addAll(it)
                    }
                }
                flowOf(new_list.toList())
            }
        }
    }

    fun addCuisine(item: String){ cuisine.value = item }

    fun addDietAndIntolerance(new_diet: List<String>, new_intolerance: List<String>){
        _diet.value = ArrayList(new_diet)
        _intolerance.value = ArrayList(new_intolerance)
    }

    fun clearCuisine(){
        cuisine.value = ""
    }

    fun clearDietAndIntolerance(){
        _diet.value = arrayListOf()
        _intolerance.value = arrayListOf()
    }

    fun onRefresh(){
        refreshed = true
    }

}

const val TAG = "App debug"
