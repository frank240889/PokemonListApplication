package com.franco.mytestapplication.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.franco.mytestapplication.R
import com.franco.mytestapplication.presentation.list.PokemonListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList(savedInstanceState)
    }

    private fun initList(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_fragment_container, PokemonListFragment.newInstance())
                .commit()
        }
    }
}