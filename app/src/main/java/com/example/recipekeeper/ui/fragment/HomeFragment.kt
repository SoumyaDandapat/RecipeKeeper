package com.example.recipekeeper.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipekeeper.R
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.databinding.FragmentHomeBinding
import com.example.recipekeeper.ui.adapter.RecipeAdapter
import com.example.recipekeeper.ui.adapter.RecipeClickInterface
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel
import com.example.recipekeeper.util.Utils.replaceFragment

class HomeFragment : Fragment() ,RecipeClickInterface{

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecipeViewModel
    private val recipeAdapter: RecipeAdapter = RecipeAdapter(context,this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        viewModel.allRecipe.observe(viewLifecycleOwner) { list ->
            list?.let {
                recipeAdapter?.updateList(it)
            }
        }

        setRecyclerView()

        val products = mutableListOf<Recipe>(
            Recipe(1,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(2,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(3,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(4,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps"),
            Recipe(5,"Pizza","desc","https://pngimg.com/uploads/pizza/pizza_PNG43991.png","some ingredients","some steps"),
            Recipe(6,"Burger","desc","https://pngimg.com/uploads/burger_sandwich/burger_sandwich_PNG4135.png","some ingredients","some steps")
        )

        products.forEach{ viewModel.addRecipe(it)}

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.search_bar)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
//                    recipeAdapter?.filter?.filter(newText)
                    recipeAdapter?.let { it.filter?.let { it.filter(newText) } }
                    return false
                }
            }
        )

    }

    private fun setRecyclerView(){
        binding.recyclerView1.layoutManager = GridLayoutManager(activity,2)
        binding.recyclerView1.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.recyclerView1.adapter= recipeAdapter
        binding.recyclerView1.setHasFixedSize(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecipeClick(recipe: Recipe) {
        val recipeDetailsFragment =RecipeDetailsFragment.newInstance()
        val bundle = Bundle()
        bundle.putSerializable("image_url",recipe.imageUrl)
        bundle.putSerializable("ingredients",recipe.ingredients)
        bundle.putSerializable("description",recipe.description)
        bundle.putSerializable("name",recipe.name)
        bundle.putSerializable("steps",recipe.steps)
        recipeDetailsFragment.arguments = bundle
        fragmentManager?.let { replaceFragment(recipeDetailsFragment, it) }

    }

}