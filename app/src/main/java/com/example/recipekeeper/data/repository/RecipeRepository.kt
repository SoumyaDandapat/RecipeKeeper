package com.example.recipekeeper.data.repository

import androidx.lifecycle.LiveData
import com.example.recipekeeper.data.dao.RecipeDao
import com.example.recipekeeper.data.models.Recipe

class RecipeRepository (private val recipeDao: RecipeDao){
    val allRecipe : LiveData<List<Recipe>> = recipeDao.getAllRecipe()

    suspend fun insert(recipe: Recipe){
        recipeDao.insert(recipe)
    }

}