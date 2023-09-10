package com.inno.tatarbyhack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                    as NavHostFragment
    }
}

