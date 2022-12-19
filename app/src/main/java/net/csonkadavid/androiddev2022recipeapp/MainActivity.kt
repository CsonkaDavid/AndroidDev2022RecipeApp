package net.csonkadavid.androiddev2022recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.csonkadavid.webdev2022recipeapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}