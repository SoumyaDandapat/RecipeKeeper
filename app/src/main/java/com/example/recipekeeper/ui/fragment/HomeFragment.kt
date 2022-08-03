package com.example.recipekeeper.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.recipekeeper.R
import com.example.recipekeeper.databinding.FragmentHomeBinding
import com.example.recipekeeper.retrofit.AppExecutors
import com.example.recipekeeper.retrofit.RemoteDataSource
import com.example.recipekeeper.ui.adapter.RecipeAdapter
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: RecipeViewModel
//    private val recipeAdapter: RecipeAdapter = RecipeAdapter(this,this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
//                    recipeAdapter.filter.filter(newText)
                    return false
                }
            }
        )

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}