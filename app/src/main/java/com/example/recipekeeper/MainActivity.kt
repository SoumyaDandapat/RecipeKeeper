package com.example.recipekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipekeeper.adapter.RecipeAdapter
import com.example.recipekeeper.adapter.RecipeClickInterface
import com.example.recipekeeper.databinding.ActivityMainBinding
import com.example.recipekeeper.recipe.RecipeViewModel
import com.example.recipekeeper.recipe.models.Recipe
import com.example.recipekeeper.retrofit.AppExecutors
import com.example.recipekeeper.retrofit.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),RecipeClickInterface{

    lateinit var viewModal:RecipeViewModel
    lateinit var binding: ActivityMainBinding
    private val executors = AppExecutors()
    private val dataSource = RemoteDataSource(this)
    private var loadingApi = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView1.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerView1.setHasFixedSize(true)

        val recipeAdapter = RecipeAdapter(this,this)

        binding.recyclerView1.adapter = recipeAdapter

        // initializing our view modal.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(RecipeViewModel::class.java)

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModal.allRecipe.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.
                recipeAdapter.updateList(it)
//                recipeAdapter.notifyDataSetChanged()
            }

        })

        //Search View
        val searchView = findViewById<SearchView>(R.id.searchView2)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recipeAdapter.filter.filter(newText)
                return false
            }

        })

        //API loading part
        if (!loadingApi)
        {
            executors.networkIO().execute{
                dataSource.api().getListOfRecipe(2,20).enqueue(object : Callback<List<Recipe>>{
                    override fun onResponse(
                        call: Call<List<Recipe>>,
                        response: Response<List<Recipe>>
                    ) {
                        loadingApi=true
                        executors.diskIO().execute{
                            val apiResultList = response.body()
                            apiResultList.let { list ->
                                list?.forEach{
                                    viewModal.addRecipe(it)
                                }
                            }

                        }


                    }

                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }


    }

    override fun onRecipeClick(recipe: Recipe) {
        val intent = Intent(this@MainActivity,DisplayActivity::class.java)
        intent.putExtra("image_url",recipe.imageUrl)
        intent.putExtra("ingredients",recipe.ingredients)
        intent.putExtra("description",recipe.description)
        intent.putExtra("name",recipe.name)
        intent.putExtra("steps",recipe.steps)
        startActivity(intent)

    }
}