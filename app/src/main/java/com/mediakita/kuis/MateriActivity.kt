package com.mediakita.kuis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mediakita.kuis.databinding.ActivityMateriBinding
import java.util.Locale

class MateriActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMateriBinding
    private lateinit var listMateri: Array<String>
    private lateinit var textToSpeech: TextToSpeech
    private var isReady = false
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listMateri = intent.getStringArrayExtra(EXTRA_MATERI) as Array<String>

        val listPhoto = when (intent.getStringExtra(EXTRA_TOPIC)) {
            "Myself" -> resources.obtainTypedArray(R.array.myself_photo)
            "Fruits" -> resources.obtainTypedArray(R.array.fruits_photo)
            "Animals" -> resources.obtainTypedArray(R.array.animals_photo)
            else -> resources.obtainTypedArray(R.array.animals_photo)
        }

        textToSpeech = TextToSpeech(this, this)

        binding.tvMateri.text = listMateri[0].uppercase()
        var photo = listPhoto.getResourceId(0, -1)
//        binding.ivMateri.setImageResource(listPhoto.getResourceId(0, -1))
        Glide.with(this)
            .load(photo)
            .apply(RequestOptions().override(600,600))
            .into(binding.ivMateri)

        binding.btnVoice.setOnClickListener {
            if (isReady) textToSpeech.speak(
                binding.tvMateri.text.toString(),
                TextToSpeech.QUEUE_FLUSH,
                null
            )
        }

        binding.cvSoalSebelumnya.setOnClickListener {
            if(index > 0) {
                index--
                binding.tvMateri.text = listMateri[index].uppercase()
                photo = listPhoto.getResourceId(index, -1)
                Glide.with(this)
                    .load(photo)
                    .apply(RequestOptions().override(600,600))
                    .into(binding.ivMateri)
            }
        }

        binding.cvSoalSelanjutnya.setOnClickListener {
            if(index < listMateri.size - 1) {
                index++
                binding.tvMateri.text = listMateri[index].uppercase()
                photo = listPhoto.getResourceId(index, -1)
                Glide.with(this)
                    .load(photo)
                    .apply(RequestOptions().override(600,600))
                    .into(binding.ivMateri)
            }
        }
    }

    companion object {
        const val EXTRA_MATERI = "extra_materi"
        const val EXTRA_TOPIC = "extra_topic"
    }

    override fun onInit(p0: Int) {
        textToSpeech.language = Locale.getDefault()
        isReady = true
    }
}