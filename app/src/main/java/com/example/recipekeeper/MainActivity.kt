package com.example.recipekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipekeeper.adapter.RecipeAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView1)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val products = arrayOf(Recipe(1,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png"),
        Recipe(2,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png"),
            Recipe(3,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png"),
            Recipe(4,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png"),
            Recipe(5,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png"),
            Recipe(6,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png"))

        recyclerView.adapter = RecipeAdapter(products)

        recyclerView.setHasFixedSize(true)

    }
}