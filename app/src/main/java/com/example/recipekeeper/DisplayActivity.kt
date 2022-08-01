package com.example.recipekeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipekeeper.databinding.RecipeDetailsBinding
import com.squareup.picasso.Picasso

class DisplayActivity : AppCompatActivity() {

    lateinit var binding: RecipeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= RecipeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Picasso.with(this).load(intent.getStringExtra("image_url")).into(binding.imageView)
        binding.description.text = intent.getStringExtra("description")
        binding.ingredients.text = intent.getStringExtra("ingredients")
        binding.steps.text = intent.getStringExtra("steps")
        binding.itemName.text = intent.getStringExtra("name")
    }

}