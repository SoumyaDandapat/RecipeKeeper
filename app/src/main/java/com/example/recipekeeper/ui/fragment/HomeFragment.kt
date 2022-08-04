package com.example.recipekeeper.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipekeeper.R
import com.example.recipekeeper.data.models.Recipe
import com.example.recipekeeper.databinding.FragmentHomeBinding
import com.example.recipekeeper.retrofit.AppExecutors
import com.example.recipekeeper.retrofit.RemoteDataSource
import com.example.recipekeeper.ui.adapter.RecipeAdapter
import com.example.recipekeeper.ui.adapter.RecipeClickInterface
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel
import com.example.recipekeeper.util.Constants.Companion.loadingApi
import com.example.recipekeeper.util.Utils.replaceFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() ,RecipeClickInterface{

    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    //API related
    private val executors = AppExecutors()
    private lateinit var dataSource: RemoteDataSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recipeAdapter = RecipeAdapter(context,this)
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        viewModel.allRecipe.observe(viewLifecycleOwner) { list ->
            list?.let {
                recipeAdapter?.updateList(it)
            }
        }

        Log.i("Info", "onCreateView: $context")
        Log.i("Info", "onCreateView: $activity")

        dataSource = context?.let { RemoteDataSource(it) }!!


        setRecyclerView()
        if(!loadingApi)
            loadApi()

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

    private fun loadApi() {
        executors.networkIO().execute {
            Log.i("Info", "loadApi: from API ")
            dataSource?.api()?.getListOfRecipe(2, 20)?.enqueue(object : Callback<List<Recipe>> {
                override fun onResponse(
                    call: Call<List<Recipe>>,
                    response: Response<List<Recipe>>
                ) {
                    loadingApi = true
                    Log.i("Info", "onResponse: from API  ")
                    executors.diskIO().execute {
                        val apiResultList = response.body()
                        apiResultList.let { list ->
                            list?.forEach {
                                viewModel.addRecipe(it)
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

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