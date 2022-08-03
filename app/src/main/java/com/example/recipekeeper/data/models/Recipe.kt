package com.example.recipekeeper.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipe_table")
data class Recipe(@PrimaryKey @ColumnInfo(name = "id") @field:SerializedName("id") val id:Int, @ColumnInfo(name = "name") @field:SerializedName("name") val name: String, @ColumnInfo(name = "description")@field:SerializedName("description") val description: String, @ColumnInfo(name = "imageurl")@field:SerializedName("imageurl") val imageUrl:String, @ColumnInfo(name = "ingredients") @field:SerializedName("ingredients") val ingredients:String, @ColumnInfo(name = "steps")@field:SerializedName("steps") val steps:String)