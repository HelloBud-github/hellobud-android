package com.mediakita.kuis

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mediakita.kuis.databinding.ActivityMateriChoiceBinding

class MateriChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMateriChoiceBinding
    private lateinit var listMateri: Array<String>
    private lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMySelfMateri.setOnClickListener {
            listMateri = resources.getStringArray(R.array.myself)
//            listPhoto = resources.obtainTypedArray(R.array.myself_photo)
            topic = "Myself"
            chooseMateri(listMateri, topic)
        }

        binding.btnFruitsMateri.setOnClickListener {
            listMateri = resources.getStringArray(R.array.fruits)
//            listPhoto = resources.obtainTypedArray(R.array.fruits_photo)
            topic = "Fruits"
            chooseMateri(listMateri, topic)
        }

        binding.btnAnimalsMateri.setOnClickListener {
            listMateri = resources.getStringArray(R.array.animals)
//            listPhoto = resources.obtainTypedArray(R.array.animals_photo)
            topic = "Animals"
            chooseMateri(listMateri, topic)
        }
    }

    private fun chooseMateri(listMateri : Array<String>, topic: String) {
        val intent = Intent(this@MateriChoiceActivity, MateriActivity::class.java)
        intent.putExtra(MateriActivity.EXTRA_MATERI, listMateri)
        intent.putExtra(MateriActivity.EXTRA_TOPIC, topic)
        startActivity(intent)
    }
}