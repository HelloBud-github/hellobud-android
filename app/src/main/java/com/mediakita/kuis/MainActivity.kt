package com.mediakita.kuis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mediakita.kuis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreference = UserPreference(this)

        if (!userPreference.getUser().isLoggedin) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnMateri.setOnClickListener {
            val intent = Intent(this@MainActivity, MateriChoiceActivity::class.java)
            startActivity(intent)
        }

        binding.bntKuis.setOnClickListener {
            val intent = Intent(this@MainActivity, KuisChoiceActivity::class.java)
            startActivity(intent)
        }
    }
}