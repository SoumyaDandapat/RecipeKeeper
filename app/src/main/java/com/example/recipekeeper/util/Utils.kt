package com.example.recipekeeper.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.recipekeeper.R

object Utils {
    fun replaceFragment(someFragment: Fragment?, fragmentManager: FragmentManager) {
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        if (someFragment != null) {
            transaction.replace(R.id.nav_host_fragment_content_main, someFragment)
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }
}