package com.example.recipekeeper.recipe

import androidx.lifecycle.LiveData
import com.example.recipekeeper.recipe.models.Recipe

class RecipeRepository (private val recipeDao: RecipeDao){
    val allRecipe : LiveData<List<Recipe>> = recipeDao.getAllRecipe()

    suspend fun insert(recipe: Recipe){
        recipeDao.insert(recipe)
    }

}