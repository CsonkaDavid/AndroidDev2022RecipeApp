package net.csonkadavid.androiddev2022recipeapp.view.model

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.launch
import net.csonkadavid.androiddev2022recipeapp.persistence.model.RecipeEntity
import net.csonkadavid.androiddev2022recipeapp.repository.RecipeRepository
import net.csonkadavid.androiddev2022recipeapp.view.fragment.AddRecipeFragmentDirections
import net.csonkadavid.androiddev2022recipeapp.view.fragment.RecipeListFragment
import net.csonkadavid.androiddev2022recipeapp.view.fragment.RecipeListFragmentDirections
import net.csonkadavid.androiddev2022recipeapp.view.fragment.SingleRecipeFragmentDirections
import net.csonkadavid.webdev2022recipeapp.R

class RecipesViewModel (private val recipeRepository: RecipeRepository, private val navController: NavController)
    :ViewModel() {

    class Factory(
        private val recipeRepository: RecipeRepository,
        private val navController: NavController) : ViewModelProvider.Factory {

        @Override
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipesViewModel(recipeRepository, navController) as T
        }
    }

    private var _recipeList = Transformations.map(recipeRepository.getRecipes()){ it }
    val recipeList : LiveData<List<RecipeEntity>>
        get() = _recipeList

    private var _addRecipeName = MutableLiveData<String>()
    val addRecipeName : LiveData<String>
        get() = _addRecipeName

    private var _addRecipeCategory = MutableLiveData<String>()
    val addRecipeCategory : LiveData<String>
        get() = _addRecipeCategory

    private var _addRecipePrepTime = MutableLiveData<String>()
    val addRecipePrepTime : LiveData<String>
        get() = _addRecipePrepTime

    private var _addRecipeIngredients = MutableLiveData<String>()
    val addRecipeIngredients : LiveData<String>
        get() = _addRecipeIngredients

    private var _addRecipeDescription = MutableLiveData<String>()
    val addRecipeDescription : LiveData<String>
        get() = _addRecipeDescription

    private var _latestRecipe = MutableLiveData<String>()
    val latestRecipe : LiveData<String>
        get() = _latestRecipe

    fun navigateToSingleRecipeFragment(view: View, recipeEntity: RecipeEntity) {
        Navigation
            .findNavController(view)
            .navigate(RecipeListFragmentDirections
                .actionRecipeListFragmentToSingleRecipeFragment(
                    recipeEntity.name,
                    recipeEntity.category,
                    recipeEntity.prepTime.toString(),
                    recipeEntity.ingredients,
                    recipeEntity.description
                ))
    }

    fun navigateToAddFragment() {
        navController.navigate(R.id.addRecipeFragment)
    }

    fun navigateToListFragment(view :View) {
        Navigation
            .findNavController(view)
            .navigate(AddRecipeFragmentDirections
                .actionAddRecipeFragmentToRecipeListFragment(addRecipeName.value!!))
    }

    fun addRecipe(name:String,
                  category:String,
                  prepTime:String,
                  ingredients:String,
                  description :String) {

        viewModelScope.launch {
            recipeRepository.saveRecipe(name, category, prepTime.toInt(), ingredients, description)
        }
    }

    fun changeAddRecipeName(s :String) {
        _addRecipeName.value = s
    }

    fun changeAddRecipeCategory(s :String) {
        _addRecipeCategory.value = s
    }

    fun changeAddRecipePrepTime(s :String) {
        _addRecipePrepTime.value = s
    }
    fun changeAddRecipeDescription(s :String) {
        _addRecipeDescription.value = s
    }

    fun changeAddRecipeIngredients(s: String) {
        _addRecipeIngredients.value = s
    }
}