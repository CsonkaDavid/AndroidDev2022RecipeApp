package net.csonkadavid.androiddev2022recipeapp.persistence.model;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Types.BLOB

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var name: String,

    var category: String,

    @ColumnInfo(name = "prep_time")
    var prepTime: Int,

    @ColumnInfo(typeAffinity = BLOB)
    var ingredients: String,

    @ColumnInfo(name = "image_uri")
    var imageUri: String,

    @ColumnInfo(typeAffinity = BLOB)
    var description: String
)
