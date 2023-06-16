package com.mediakita.kuis

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mediakita.kuis.databinding.ActivityJawabanKuisBinding
import java.lang.IndexOutOfBoundsException
import java.util.Locale


class JawabanKuisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJawabanKuisBinding
    private val randomSoal = arrayListOf<Int>()
    private lateinit var listPhoto: TypedArray
    private var nomorSoal = 1

    private var listMateri: ArrayList<String>? = null
    private var nilai = 0
    private lateinit var listTerjawab: Array<Boolean?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJawabanKuisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listMateri = intent.getStringArrayListExtra(EXTRA_MATERI)

        listTerjawab = arrayOfNulls(listMateri!!.size)

        val soalGenerator = arrayListOf<Int>()
        for (item in listMateri!!.indices) {
            soalGenerator.add(item)
        }
        soalGenerator.shuffle()

        for (soal in soalGenerator) {
            randomSoal.add(soal)
        }

        setSoal()

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermissions()
        }
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        binding.cvJawab.setOnClickListener {
            startActivityForResult(speechRecognizerIntent, REQUEST_CODE_SPEECH_INPUT)

        }

        binding.cvSoalSelanjutnya.setOnClickListener {
            nextSoal()
        }

        binding.cvSoalSebelumnya.setOnClickListener {
            if (nomorSoal > 1) {
                nomorSoal--
                setSoal()
            }
        }
    }

    private fun nextSoal() {
        if (nomorSoal < listMateri!!.size) {
            nomorSoal++
            setSoal()
        } else {
            val intent = Intent(this@JawabanKuisActivity, NilaiActivity::class.java)
            intent.putExtra(NilaiActivity.EXTRA_KATEGORI, "Voice")
            intent.putExtra(NilaiActivity.EXTRA_NILAI, nilai)
            startActivity(intent)
        }
    }

    private fun setSoal() {
        if (listTerjawab[nomorSoal - 1] != null) {
            binding.tvBenarKamuHebat.visibility = View.VISIBLE
            binding.cvJawab.isEnabled = false
            if (listTerjawab[nomorSoal-1] == true) {
                binding.tvBenarKamuHebat.setTextColor(getColor(R.color.green1))
                binding.tvBenarKamuHebat.text = "BENAR!         KAMU HEBAT"
            }
            else {
                binding.tvBenarKamuHebat.setTextColor(getColor(android.R.color.holo_red_dark))
                binding.tvBenarKamuHebat.text = "SALAH!         COBA LAGI"
            }
        }
        else {
            binding.tvBenarKamuHebat.visibility = View.GONE
            binding.cvJawab.isEnabled = true
        }

        listPhoto = when (randomSoal[nomorSoal - 1]) {
            in 0..4 -> resources.obtainTypedArray(R.array.myself_photo)
            in 5..9 -> resources.obtainTypedArray(R.array.fruits_photo)
            in 10..14 -> resources.obtainTypedArray(R.array.animals_photo)
            else -> resources.obtainTypedArray(R.array.animals_photo)
        }
        binding.textView9.text =
            getString(R.string.soal_suara, nomorSoal, listMateri!![randomSoal[nomorSoal - 1]])

        var soalNow = randomSoal[nomorSoal - 1]

        when (randomSoal[nomorSoal - 1]) {
            in 5..9 -> soalNow -= 5
            in 10..14 -> soalNow -= 10
        }

        val photo = listPhoto.getResourceId(soalNow, -1)
        Glide.with(this)
            .load(photo)
            .apply(RequestOptions().override(600, 600))
            .into(binding.imageView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.tvBenarKamuHebat.visibility = View.VISIBLE
            if (listMateri!![randomSoal[nomorSoal - 1]].lowercase() == result!![0]) {
                listTerjawab[nomorSoal - 1] = true
                nilai += (100.0/listMateri!!.size.toDouble()).toInt()
                binding.tvBenarKamuHebat.setTextColor(getColor(R.color.green1))
                binding.tvBenarKamuHebat.text = "BENAR!         KAMU HEBAT"
            } else {
                listTerjawab[nomorSoal - 1] = false
                binding.tvBenarKamuHebat.setTextColor(getColor(android.R.color.holo_red_dark))
                binding.tvBenarKamuHebat.text = "SALAH!         COBA LAGI"
            }
            binding.cvJawab.isEnabled = false
        }
    }

    private fun checkPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.RECORD_AUDIO), RecordAudioRequestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_MATERI = "extra_materi"
        const val RecordAudioRequestCode = 1
        const val REQUEST_CODE_SPEECH_INPUT = 1000
    }
}