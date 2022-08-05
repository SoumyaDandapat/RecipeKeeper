package com.example.recipekeeper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.retrofit.AppExecutors
import com.example.recipekeeper.retrofit.RemoteDataSource
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel
import com.example.recipekeeper.util.Constants.Companion.SHARED_PREFERENCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    //API related
    private val executors = AppExecutors()
    private val dataSource =  RemoteDataSource(this)
    private lateinit var viewModel: RecipeViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val hasData = sharedPreferences.getBoolean("hasData",false)


        CoroutineScope(Dispatchers.IO).launch {
            if (!hasData)
            {
                loadApi()
                editor = sharedPreferences.edit()
                editor.putBoolean("hasData",true)
                editor.commit()
            }
            delay(1000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun loadApi() {
        executors.networkIO().execute {
            Log.i("Info", "loadApi: from API ")
            dataSource.api().getListOfRecipe(2, 20).enqueue(object : Callback<List<Recipe>> {
                override fun onResponse(
                    call: Call<List<Recipe>>,
                    response: Response<List<Recipe>>
                ) {
                    Log.i("Info", "onResponse: from API  ")
                    executors.diskIO().execute {
                        val apiResultList = response.body()
                        apiResultList.let { list ->
                            list?.forEach {
                                viewModel.addRecipe(it)
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                    Log.i("Info", "onFailure: from API $t ")
                }
            })
        }

    }
}