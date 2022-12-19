package net.csonkadavid.androiddev2022recipeapp.repository

import net.csonkadavid.androiddev2022recipeapp.persistence.model.RecipeEntity
import net.csonkadavid.androiddev2022recipeapp.persistence.repository.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

    fun getRecipes() = recipeDao.getAllRecipes()

    suspend fun saveRecipe(
        name :String,
        category: String,
        prepTime :Int,
        ingredients :String,
        description: String) {

        recipeDao
            .insert(RecipeEntity(0,
                name, category, prepTime, ingredients ,description)
            )
    }
}