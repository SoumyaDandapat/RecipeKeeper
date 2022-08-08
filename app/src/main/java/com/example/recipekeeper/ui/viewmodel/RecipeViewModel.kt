package com.example.recipekeeper.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipekeeper.data.database.RecipeDatabase
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.data.repository.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application):AndroidViewModel(application){

    val allRecipe : LiveData<List<Recipe>>
    val favouriteRecipe : LiveData<List<Recipe>>
    val repository : RecipeRepository
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        val dao = RecipeDatabase.getDatabase(application).getRecipeDao()
        repository = RecipeRepository(dao)
        allRecipe = repository.allRecipe
        favouriteRecipe = repository.favouriteRecipe
    }

    fun addRecipe(recipe: Recipe) =viewModelScope.launch(dispatcher) {
        repository.insert(recipe)
    }

    fun updateFavouriteStatus(id:Int,isFav:Boolean) =viewModelScope.launch(dispatcher) {
        repository.updateFavourite(id, isFav)
    }


}