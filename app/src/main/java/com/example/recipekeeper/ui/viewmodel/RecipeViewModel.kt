package com.example.recipekeeper.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipekeeper.data.database.RecipeDatabase
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application):AndroidViewModel(application){

    val allRecipe : LiveData<List<Recipe>>
    val repository : RecipeRepository

    init {
        val dao = RecipeDatabase.getDatabase(application).getRecipeDao()
        repository = RecipeRepository(dao)
        allRecipe = repository.allRecipe
    }

    fun addRecipe(recipe: Recipe) =viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }


}