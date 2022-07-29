package com.example.recipekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipekeeper.adapter.RecipeAdapter
import com.example.recipekeeper.adapter.RecipeClickInterface
import com.example.recipekeeper.recipe.RecipeViewModel
import com.example.recipekeeper.recipe.models.Recipe
import kotlin.math.log

class MainActivity : AppCompatActivity(),RecipeClickInterface {

    lateinit var viewModal:RecipeViewModel
    lateinit var recipeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipeRecyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
        recipeRecyclerView.layoutManager = GridLayoutManager(this,2)
        recipeRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val products = mutableListOf<Recipe>(
            Recipe(1,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(2,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(3,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(4,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(5,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(6,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps")
        )



        recipeRecyclerView.setHasFixedSize(true)

        val recipeAdapter = RecipeAdapter(this,this)

        recipeRecyclerView.adapter = recipeAdapter

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