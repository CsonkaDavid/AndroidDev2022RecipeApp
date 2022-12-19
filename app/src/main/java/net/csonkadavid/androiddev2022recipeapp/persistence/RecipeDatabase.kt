package net.csonkadavid.androiddev2022recipeapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.csonkadavid.androiddev2022recipeapp.persistence.model.RecipeEntity
import net.csonkadavid.androiddev2022recipeapp.persistence.repository.RecipeDao

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase :RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipeDatabase.db"
                    )
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}