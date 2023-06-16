package com.mediakita.kuis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mediakita.kuis.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.edtUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edtPassword.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            val database = Firebase.database("https://hellobud-id-default-rtdb.asia-southeast1.firebasedatabase.app")
            val myRef = database.getReference("user").child(username)

            myRef.get().addOnSuccessListener {
                if (it.value == null) {
                    Toast.makeText(this, "Username/password salah", Toast.LENGTH_SHORT).show()
                }
                else {
                    val value = it.value as Map<*, *>
                    if(value["password"] == password) {
                        val userModel = UserModel(username, value["email"] as String?, true)
                        val userPreference = UserPreference(this)

                        userPreference.setUser(userModel)
                        Toast.makeText(this, "Selamat datang $username", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Username/password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        binding.tvDaftar.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}