package com.mediakita.kuis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mediakita.kuis.databinding.ActivityKuisChoiceBinding

class KuisChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKuisChoiceBinding
    private lateinit var listMateri: Array<String>
    private var listMateriVoice = arrayListOf<String>()
    private lateinit var topic: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMySelfKuis.setOnClickListener {
            listMateri = resources.getStringArray(R.array.myself)
//            listPhoto = resources.obtainTypedArray(R.array.myself_photo)
            topic = "Myself"
            chooseMateri(listMateri, topic)
        }

        binding.btnFruitsKuis.setOnClickListener {
            listMateri = resources.getStringArray(R.array.fruits)
//            listPhoto = resources.obtainTypedArray(R.array.fruits_photo)
            topic = "Fruits"
            chooseMateri(listMateri, topic)
        }

        binding.btnAnimalsKuis.setOnClickListener {
            listMateri = resources.getStringArray(R.array.animals)
//            listPhoto = resources.obtainTypedArray(R.array.animals_photo)
            topic = "Animals"
            chooseMateri(listMateri, topic)
        }

        binding.btnVoiceKuis.setOnClickListener {
            listMateriVoice.addAll(resources.getStringArray(R.array.myself))
            listMateriVoice.addAll(resources.getStringArray(R.array.fruits))
            listMateriVoice.addAll(resources.getStringArray(R.array.animals))

//            listPhoto = resources.obtainTypedArray(R.array.animals_photo)
            val intent = Intent(this@KuisChoiceActivity, JawabanKuisActivity::class.java)
            intent.putExtra(JawabanKuisActivity.EXTRA_MATERI, listMateriVoice)
            startActivity(intent)
        }
    }

    private fun chooseMateri(listMateri : Array<String>, topic: String) {
        val intent = Intent(this@KuisChoiceActivity, KuisGambarActivity::class.java)
        intent.putExtra(KuisGambarActivity.EXTRA_MATERI, listMateri)
        intent.putExtra(KuisGambarActivity.EXTRA_TOPIC, topic)
        startActivity(intent)
    }
}