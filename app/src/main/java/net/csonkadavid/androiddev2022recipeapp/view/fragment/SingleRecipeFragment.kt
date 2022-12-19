package net.csonkadavid.androiddev2022recipeapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.csonkadavid.androiddev2022recipeapp.persistence.RecipeDatabase
import net.csonkadavid.androiddev2022recipeapp.repository.RecipeRepository
import net.csonkadavid.androiddev2022recipeapp.view.model.RecipesViewModel
import net.csonkadavid.webdev2022recipeapp.databinding.FragmentSingleRecipeBinding

class SingleRecipeFragment : Fragment() {

    private lateinit var viewModel :RecipesViewModel
    private lateinit var binding: FragmentSingleRecipeBinding

    private val args: SingleRecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleRecipeBinding.inflate(inflater, container, false)

        setupViewModel()

        binding.viewModel = viewModel

        binding.singleRecipeName.text = args.clickedRecipeName
        binding.singleRecipeCategory.text = args.clickedRecipeCategory
        binding.singleRecipePrepTime.text = args.clickedRecipePrepTime
        binding.singleRecipeIngredients.text = args.clickedRecipeIngredients
        binding.singleRecipeDescription.text = args.clickedRecipeDescription

        binding.floatingActionButton3.setOnClickListener {
            viewModel.deleteRecipe(
                "",
                "",
                "",
                "",
                "",
                "")
        }

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            RecipesViewModel.Factory(
                RecipeRepository(
                    RecipeDatabase.getInstance(requireContext()).recipeDao), findNavController())
        )[RecipesViewModel::class.java]
    }
}