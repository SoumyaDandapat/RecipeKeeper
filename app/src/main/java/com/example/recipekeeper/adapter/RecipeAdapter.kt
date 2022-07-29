package com.example.recipekeeper.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipekeeper.R
import com.example.recipekeeper.recipe.models.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(val context: Context,val recipeClickInterface: RecipeClickInterface) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private val allRecipe = ArrayList<Recipe>()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val recipeTitle : TextView = itemView.findViewById(R.id.recipeTitle)
        val recipeImage : ImageView = itemView.findViewById(R.id.recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        Log.i("debug", "onCreateViewHolder: ")
        val v = LayoutInflater.from( parent.context  ).inflate(R.layout.recipe_card,parent,false)
        return ViewHolder( v )
    }

    override fun getItemCount(): Int {
        return allRecipe.size
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        Log.i("debug", "onBindViewHolder: $position")
        holder.apply {
            recipeTitle.text = allRecipe[position].name
            Picasso.with(itemView.context).load(allRecipe[position].imageUrl).into(recipeImage)
        }
        holder.itemView.setOnClickListener{
            recipeClickInterface.onRecipeClick(allRecipe.get(position))
        }
    }

    fun updateList(newList:List<Recipe>)
    {
        Log.i("List Changed", "updateList: $newList")
        allRecipe.clear()

        allRecipe.addAll(newList)
        //notify data change
        notifyDataSetChanged()
    }

}

interface  RecipeClickInterface {
    fun onRecipeClick(recipe: Recipe)
}
