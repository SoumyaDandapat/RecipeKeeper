package com.example.recipekeeper.recipe.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(@PrimaryKey @ColumnInfo(name = "id") val id:Int,@ColumnInfo(name = "name") val name: String,@ColumnInfo(name = "description") val description: String,@ColumnInfo(name = "imageurl") val imageUrl:String,@ColumnInfo(name = "ingredients") val ingredients:String,@ColumnInfo(name = "steps") val steps:String)