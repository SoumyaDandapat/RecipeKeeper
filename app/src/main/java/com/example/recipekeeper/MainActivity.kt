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

class MainActivity : AppCompatActivity(),RecipeClickInterface{

    lateinit var viewModal:RecipeViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView1.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val products = mutableListOf<Recipe>(
            Recipe(1,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(2,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(3,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(4,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(5,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(6,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps")
        )


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

        //        Initially pushing values
        products.forEach{ viewModal.addRecipe(it)}
//        Log.i("debug", "Recipe count ${recipeAdapter.itemCount} ")

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