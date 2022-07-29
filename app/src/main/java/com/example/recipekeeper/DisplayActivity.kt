package com.example.recipekeeper

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_details)

        val recipeImage = findViewById<ImageView>(R.id.imageView)
        val description = findViewById<TextView>(R.id.description)
        val ingredients = findViewById<TextView>(R.id.ingredients)
        val steps = findViewById<TextView>(R.id.steps)
        val name = findViewById<TextView>(R.id.item_name)

        Picasso.with(this).load(intent.getStringExtra("image_url")).into(recipeImage)
        description.text = intent.getStringExtra("description")
        ingredients.text = intent.getStringExtra("ingredients")
        steps.text = intent.getStringExtra("steps")
        name.text = intent.getStringExtra("name")
    }

}