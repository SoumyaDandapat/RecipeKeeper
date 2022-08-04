package com.example.recipekeeper

//import com.example.recipekeeper.ui.adapter.RecipeClickInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipekeeper.databinding.ActivityMainBinding
import com.example.recipekeeper.retrofit.AppExecutors
import com.example.recipekeeper.retrofit.RemoteDataSource
import com.example.recipekeeper.ui.viewmodel.RecipeViewModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){

    lateinit var viewModal: RecipeViewModel
    lateinit var binding: ActivityMainBinding
    private val executors = AppExecutors()
    private val dataSource = RemoteDataSource(this)
    private var loadingApi = false
    private lateinit var appBarConfiguration: AppBarConfiguration

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding= ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        binding.recyclerView1.layoutManager = GridLayoutManager(this,2)
//        binding.recyclerView1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        binding.recyclerView1.setHasFixedSize(true)
//
//        val recipeAdapter = RecipeAdapter(this,this)
//
//        binding.recyclerView1.adapter = recipeAdapter
//
//        // initializing our view modal.
//        viewModal = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        ).get(RecipeViewModel::class.java)
//
//        // on below line we are calling all notes method
//        // from our view modal class to observer the changes on list.
//        viewModal.allRecipe.observe(this, Observer { list ->
//            list?.let {
//                // on below line we are updating our list.
//                recipeAdapter.updateList(it)
////                recipeAdapter.notifyDataSetChanged()
//            }
//
//        })
//
//        //Search View
//        val searchView = findViewById<SearchView>(R.id.searchView2)
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                recipeAdapter.filter.filter(newText)
//                return false
//            }
//
//        })
//
//        //API loading part
//        if (!loadingApi)
//        {
//            executors.networkIO().execute{
//                dataSource.api().getListOfRecipe(2,20).enqueue(object : Callback<List<Recipe>>{
//                    override fun onResponse(
//                        call: Call<List<Recipe>>,
//                        response: Response<List<Recipe>>
//                    ) {
//                        loadingApi=true
//                        executors.diskIO().execute{
//                            val apiResultList = response.body()
//                            apiResultList.let { list ->
//                                list?.forEach{
//                                    viewModal.addRecipe(it)
//                                }
//                            }
//
//                        }
//                    }
//                    override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
//                        TODO("Not yet implemented")
//                    }
//                })
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}