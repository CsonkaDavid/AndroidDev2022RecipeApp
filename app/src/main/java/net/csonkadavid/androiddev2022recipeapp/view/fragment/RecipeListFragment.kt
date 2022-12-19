package net.csonkadavid.androiddev2022recipeapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.csonkadavid.androiddev2022recipeapp.persistence.RecipeDatabase
import net.csonkadavid.androiddev2022recipeapp.repository.RecipeRepository
import net.csonkadavid.androiddev2022recipeapp.view.model.RecipesViewModel
import net.csonkadavid.androiddev2022recipeapp.view.recycler.IClickableRecycler
import net.csonkadavid.androiddev2022recipeapp.view.recycler.RecipeListRecyclerViewAdapter
import net.csonkadavid.webdev2022recipeapp.databinding.FragmentRecipeListBinding

class RecipeListFragment : Fragment(), IClickableRecycler {

    private lateinit var viewModel: RecipesViewModel
    private lateinit var recyclerViewAdapter: RecipeListRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding : FragmentRecipeListBinding

    private val args: RecipeListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRecipeListBinding.inflate(inflater)

        binding.lifecycleOwner = this

        setupRecyclerView()
        setupViewModel()
        setupObservers()

        binding.recipesViewModel = viewModel

        binding.latestRecipeText.text = args.latestRecipeName

        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recipeListRecyclerView
        recyclerViewAdapter = RecipeListRecyclerViewAdapter(arrayListOf(), this)

        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
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
        viewModel.recipeList.observe(this.viewLifecycleOwner) {
            recyclerViewAdapter.addRecipeList(it)

            it.let {
                recyclerViewAdapter.addHeaderAndSubmitList(it)
            }
        }
    }

    @Override
    override fun onItemClick(position: Int) {
        val clickedEntity = viewModel.recipeList.value!![position-1]

        viewModel.navigateToSingleRecipeFragment(binding.root, clickedEntity)
    }
}