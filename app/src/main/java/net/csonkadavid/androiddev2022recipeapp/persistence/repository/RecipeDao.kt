package net.csonkadavid.androiddev2022recipeapp.persistence.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.csonkadavid.androiddev2022recipeapp.persistence.model.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAllRecipes(): LiveData<List<RecipeEntity>>

    @Insert
    suspend fun insert(recipeEntity: RecipeEntity)
}