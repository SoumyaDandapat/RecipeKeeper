package com.example.recipekeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_details)
    }

}