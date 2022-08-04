package com.example.recipekeeper.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipekeeper.recipe.models.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("Select * from recipe_table order by id ASC")
    fun getAllRecipe(): LiveData<List<Recipe>>

    @Query("Delete from recipe_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(recipe: Recipe)

}