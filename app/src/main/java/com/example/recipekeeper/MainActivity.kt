package com.example.recipekeeper

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipekeeper.databinding.ActivityMainBinding
import com.example.recipekeeper.ui.fragment.HomeFragment
import com.example.recipekeeper.util.Constants.Companion.EXIT_MESSAGE
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

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
                R.id.nav_home, R.id.nav_profile,R.id.nav_favourite, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
        {
            val currentFragment = supportFragmentManager.currentNavigationFragment()
            if(currentFragment !is HomeFragment)
                super.onBackPressed()
            else
            {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.apply {
                    this.setMessage(EXIT_MESSAGE)
                    this.setCancelable(false)
                    this.setPositiveButton("Yes") { _, _ -> finish() }
                    this.setNegativeButton("No"){dialogInterface,_ -> dialogInterface.cancel() }
                }
                val alert: AlertDialog = alertDialogBuilder.create()
                alert.show()
            }
        }
    }

    private fun FragmentManager.currentNavigationFragment(): Fragment?{
        return primaryNavigationFragment?.childFragmentManager?.fragments?.first()
    }

}