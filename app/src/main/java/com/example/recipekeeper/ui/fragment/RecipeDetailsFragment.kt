package com.example.recipekeeper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipekeeper.databinding.FragmentRecipeDetailsBinding
import com.example.recipekeeper.ui.viewmodel.RecipeDetailsViewModel
import com.squareup.picasso.Picasso

class RecipeDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeDetailsFragment()
    }

    private lateinit var viewModel: RecipeDetailsViewModel
    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        val bundle: Bundle? = arguments
        bundle?.let {
            Picasso.with(context).load(it.getString("image_url")).into(binding.imageView)
            binding.description.text = it.getString("description")
            binding.ingredients.text = it.getString("ingredients")
            binding.steps.text = it.getString("steps")
            binding.itemName.text = it.getString("name")
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}