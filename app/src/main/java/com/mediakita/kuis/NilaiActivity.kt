package com.mediakita.kuis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mediakita.kuis.databinding.ActivityNilaiBinding

class NilaiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNilaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNilaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nilai = intent.getIntExtra(EXTRA_NILAI, 0)
        val kategori = intent.getStringExtra(EXTRA_KATEGORI)
        binding.tvNilai.text = "$nilai"

        val username = UserPreference(this).getUser().username

        val database = Firebase.database("https://hellobud-id-default-rtdb.asia-southeast1.firebasedatabase.app")
        val myRef = database.getReference("nilai").child(username.toString())

        myRef.child(kategori.toString()).setValue(nilai)

        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this@NilaiActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_NILAI = "extra_nilai"
        const val EXTRA_KATEGORI = "extra_kategori"
    }
}