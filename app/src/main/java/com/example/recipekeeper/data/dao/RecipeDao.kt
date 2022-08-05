package com.example.recipekeeper.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipekeeper.data.models.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("Select * from recipe_table order by id ASC")
    fun getAllRecipe(): LiveData<List<Recipe>>

    @Query("Select * from recipe_table where favourite = 1 order by id ASC")
    fun getFavouriteRecipe(): LiveData<List<Recipe>>

    @Query("Delete from recipe_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(recipe: Recipe)

    @Query ("UPDATE recipe_table SET favourite=:isFav WHERE id= :id")
    fun changeFavouriteStatus(id: Int,isFav:Boolean)

}