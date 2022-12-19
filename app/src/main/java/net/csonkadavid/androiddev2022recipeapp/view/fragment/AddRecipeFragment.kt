package net.csonkadavid.androiddev2022recipeapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import net.csonkadavid.androiddev2022recipeapp.persistence.RecipeDatabase
import net.csonkadavid.androiddev2022recipeapp.repository.RecipeRepository
import net.csonkadavid.androiddev2022recipeapp.view.model.RecipesViewModel
import net.csonkadavid.webdev2022recipeapp.R
import net.csonkadavid.webdev2022recipeapp.databinding.FragmentAddRecipeBinding
import net.csonkadavid.webdev2022recipeapp.databinding.FragmentRecipeListBinding

class AddRecipeFragment : Fragment() {

    private lateinit var viewModel :RecipesViewModel
    private lateinit var binding: FragmentAddRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddRecipeBinding.inflate(inflater, container,false)

        setupViewModel()

        binding.viewModel = viewModel

        setupObservers()
        setupTextChanges()
        setupListeners()

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

    private fun setupObservers() {
        viewModel.addRecipeName.observe(viewLifecycleOwner) {
            binding.recipeName = it
        }

        viewModel.addRecipeCategory.observe(viewLifecycleOwner) {
            binding.category = it
        }

        viewModel.addRecipePrepTime.observe(viewLifecycleOwner) {
            binding.prepTime = it
        }

        viewModel.addRecipeIngredients.observe(viewLifecycleOwner) {
            binding.ingredients = it
        }

        viewModel.addRecipeDescription.observe(viewLifecycleOwner) {
            binding.description = it
        }
    }

    private fun setupTextChanges() {
        binding.editRecipeName.doOnTextChanged { text, start, before, count ->
            println(text)
            viewModel.changeAddRecipeName(text.toString())
        }

        binding.editRecipeCategory.doOnTextChanged { text, start, before, count ->
            viewModel.changeAddRecipeCategory(text.toString())
        }

        binding.editRecipePrepTime.doOnTextChanged { text, start, before, count ->
            viewModel.changeAddRecipePrepTime(text.toString())
        }

        binding.editRecipeIngredients.doOnTextChanged { text, start, before, count ->
            viewModel.changeAddRecipeIngredients(text.toString())
        }

        binding.editRecipeDescription.doOnTextChanged { text, start, before, count ->
            viewModel.changeAddRecipeDescription(text.toString())
        }
    }

    private fun setupListeners() {
        binding.floatingActionButton.setOnClickListener {
            viewModel.addRecipe(
                binding.recipeName!!,
                binding.category!!,
                binding.prepTime!!,
                binding.ingredients!!,
                binding.description!!)

            viewModel.navigateToListFragment(binding.root)
        }
    }
}