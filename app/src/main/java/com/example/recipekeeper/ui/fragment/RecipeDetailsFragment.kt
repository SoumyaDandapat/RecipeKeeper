package com.example.recipekeeper.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
        val args: RecipeDetailsFragmentArgs by navArgs()
        args.apply{
            Picasso.with(context).load(this.recipe.imageUrl).into(binding.imageView)
            binding.description.text = this.recipe.description
            binding.ingredients.text = this.recipe.ingredients
            binding.steps.text = this.recipe.steps
            binding.itemName.text = this.recipe.name
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}