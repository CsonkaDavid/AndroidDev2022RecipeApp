package net.csonkadavid.webdev2022recipeapp.persistence.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var name: String,

    var category: String,

    var img: String
)
