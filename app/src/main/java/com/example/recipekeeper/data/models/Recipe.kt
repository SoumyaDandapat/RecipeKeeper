package com.example.recipekeeper.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipe_table")
data class Recipe(@PrimaryKey val id:Int, val name: String,val description: String,val imageUrl:String, val ingredients:String, val steps:String, val favourite:Boolean = false) :
    Serializable