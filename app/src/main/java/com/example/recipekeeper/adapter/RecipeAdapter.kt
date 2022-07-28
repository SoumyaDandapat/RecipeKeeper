package com.example.recipekeeper.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipekeeper.DisplayActivity
import com.example.recipekeeper.R
import com.example.recipekeeper.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(private var recipeArray: Array<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(recipeView: View):RecyclerView.ViewHolder(recipeView){

        val recipeTitle : TextView = recipeView.findViewById(R.id.recipeTitle)
        val recipeImage : ImageView = recipeView.findViewById(R.id.recipeImage)
        init {

            itemView.setOnClickListener {
                    v: View ->
                var b: Bundle = Bundle();
//                b.putString("product",pName.text.toString())
                Log.d("DEBUG", ": about to send intent")

                val productIntent = Intent(recipeView.context, DisplayActivity::class.java)
//                productIntent.putExtras(b)
                recipeView.context.startActivity(productIntent)

                //intent to move to ingredients page
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        val v = LayoutInflater.from( parent.context  ).inflate(R.layout.recipe_card,parent,false)
        return ViewHolder( v )
    }

    override fun getItemCount(): Int {
        return recipeArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            recipeTitle.text = recipeArray[position].name
            Picasso.with(itemView.context).load(recipeArray[position].imageUrl).into(recipeImage)
        }
    }

}