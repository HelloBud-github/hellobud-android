package com.mediakita.kuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mediakita.kuis.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val username = binding.edtUsernameRegister.text.toString().trim()
            val password = binding.edtPasswordRegister.text.toString().trim()

            if (email.isEmpty()) {
                binding.edtEmail.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                binding.edtUsernameRegister.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edtPasswordRegister.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            val database = Firebase.database("https://hellobud-id-default-rtdb.asia-southeast1.firebasedatabase.app")
            val myRef = database.getReference("user").child(username)

            myRef.get().addOnSuccessListener {
                if (it.value == null) {
                    myRef.child("email").setValue(email)
                    myRef.child("password").setValue(password)
                    Toast.makeText(this, "Daftar berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else {
                    Toast.makeText(this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }

        binding.tvMasuk.setOnClickListener {
            finish()
        }
    }
}