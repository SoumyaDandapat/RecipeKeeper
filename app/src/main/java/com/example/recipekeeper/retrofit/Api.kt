package com.example.recipekeeper.retrofit

import com.example.recipekeeper.recipe.models.Recipe
import com.example.recipekeeper.util.Constants.Companion.MOCK_JSON_FILE_NAME
import ir.logicbase.mockfit.Mock
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @Mock(MOCK_JSON_FILE_NAME)
    @GET("list")
    fun getListOfRecipe(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<List<Recipe>>
}