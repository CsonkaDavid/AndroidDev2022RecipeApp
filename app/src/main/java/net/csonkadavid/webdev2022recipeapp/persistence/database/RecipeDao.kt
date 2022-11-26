package net.csonkadavid.webdev2022recipeapp.persistence.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes where category = :cat")
    suspend fun getAllRecipesFromCategory(cat: String): List<Recipe>

    @Insert
    suspend fun insert(recipe: Recipe)
}