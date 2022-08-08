package com.example.recipekeeper.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipekeeper.R
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.databinding.FragmentFavouriteBinding
import com.example.recipekeeper.ui.adapter.RecipeAdapter
import com.example.recipekeeper.ui.adapter.RecipeClickInterface
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel

class FavouriteFragment : Fragment(),RecipeClickInterface {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        recipeAdapter = RecipeAdapter(context,this)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        viewModel.favouriteRecipe.observe(viewLifecycleOwner) { list ->
            list?.let {
                recipeAdapter?.updateList(it)
            }
        }

        setRecyclerView()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                    recipeAdapter?.let { it.filter?.let { it.filter(newText) } }
                    return false
                }
            }
        )

    }

    private fun setRecyclerView(){
        binding.recyclerViewFav.layoutManager = GridLayoutManager(activity,2)
        binding.recyclerViewFav.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        binding.recyclerViewFav.adapter= recipeAdapter
        binding.recyclerViewFav.setHasFixedSize(true)
    }

    override fun onRecipeClick(recipe: Recipe) {
        val action = FavouriteFragmentDirections.actionNavFavouriteToRecipeDetailsFragment(recipe)
        findNavController().navigate(action)

    }

    override fun onToggle(id:Int, changedState: Boolean) {
        viewModel.updateFavouriteStatus(id,changedState)
    }

}