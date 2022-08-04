package com.example.recipekeeper.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.databinding.RecipeCardBinding
import com.squareup.picasso.Picasso

class RecipeAdapter(val context: Context?,val recipeClickInterface: RecipeClickInterface) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(),Filterable {

    private var allRecipe = ArrayList<Recipe>()
    private var allRecipeFiltered = ArrayList<Recipe>()


    inner class ViewHolder(val binding: RecipeCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        val binding = RecipeCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return allRecipeFiltered.size
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        Log.i("debug", "onBindViewHolder: $position")
        holder.apply {
            binding.recipeTitle.text = allRecipeFiltered[position].name
            Picasso.with(itemView.context).load(allRecipeFiltered[position].imageUrl).into(binding.recipeImage)
        }
        holder.itemView.setOnClickListener{
            recipeClickInterface.onRecipeClick(allRecipeFiltered.get(position))
        }
    }

    fun updateList(newList:List<Recipe>)
    {
        Log.i("List Changed", "updateList: $newList")
        allRecipe = newList as ArrayList<Recipe>
        allRecipeFiltered = allRecipe
        //notify data change
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
     return object : Filter() {
         override fun performFiltering(constraint: CharSequence?): FilterResults {
             val charSearch = constraint.toString()?:""
             allRecipeFiltered = if (charSearch.isEmpty())
                 allRecipe
             else{
                 val filterList = ArrayList<Recipe>()
                 allRecipe.filter { it.name.contains(constraint!!,ignoreCase = true) }
                     .forEach{filterList.add(it)}
                 filterList
             }
             return  FilterResults().apply { values = allRecipeFiltered }
         }

         override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
             allRecipeFiltered = if (results?.values == null)
                 ArrayList()
             else
                 results.values as ArrayList<Recipe>
             notifyDataSetChanged()
         }

     }
    }

}

interface  RecipeClickInterface {
    fun onRecipeClick(recipe: Recipe)
}
