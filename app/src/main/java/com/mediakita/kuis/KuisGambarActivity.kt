package com.mediakita.kuis

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mediakita.kuis.databinding.ActivityKuisGambarBinding

class KuisGambarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKuisGambarBinding
    private var nomorSoal = 1
    private lateinit var listMateri: Array<String>
    private lateinit var listPhoto: TypedArray
    private var nilai = 0
    private val randomSoal = arrayListOf<Int>()
    private val listJawaban = arrayListOf<Array<Int>>()
    private var kategori = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisGambarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listMateri = intent.getStringArrayExtra(MateriActivity.EXTRA_MATERI) as Array<String>

        kategori = intent.getStringExtra(MateriActivity.EXTRA_TOPIC).toString()

        listPhoto = when (kategori) {
            "Myself" -> resources.obtainTypedArray(R.array.myself_photo)
            "Fruits" -> resources.obtainTypedArray(R.array.fruits_photo)
            "Animals" -> resources.obtainTypedArray(R.array.animals_photo)
            else -> resources.obtainTypedArray(R.array.animals_photo)
        }

        val soalGenerator = arrayListOf<Int>()
        for (item in listMateri.indices) {
            soalGenerator.add(item)
        }
        soalGenerator.shuffle()

        for (soal in soalGenerator) {
            randomSoal.add(soal)
        }

        for (item in listMateri.indices) {
            val jawabanGenerator = arrayListOf<Int>()
            for (j in listMateri.indices) {
                jawabanGenerator.add(j)
            }
            jawabanGenerator.remove(soalGenerator[item])
            jawabanGenerator.shuffle()
            val random = arrayOf(0,0,0,0)
            random[0] = soalGenerator[item]
            for (i in 1 until 4) {
                random[i] = jawabanGenerator[i-1]
            }
            random.shuffle()
            listJawaban.add(random)
            Log.d("List Jawaban", listJawaban.toString())
        }

        setSoal()

        binding.ivJawaban1.setOnClickListener {
            if (listJawaban[nomorSoal-1][0] == randomSoal[nomorSoal-1]) nilai += (100.0/ listMateri.size.toDouble()).toInt()
            nextSoal()

        }
        binding.ivJawaban2.setOnClickListener {
            if (listJawaban[nomorSoal-1][1] == randomSoal[nomorSoal-1]) nilai += (100.0/ listMateri.size.toDouble()).toInt()
            nextSoal()
        }
        binding.ivJawaban3.setOnClickListener {
            if (listJawaban[nomorSoal-1][2] == randomSoal[nomorSoal-1]) nilai += (100.0/ listMateri.size.toDouble()).toInt()
            nextSoal()
        }
        binding.ivJawaban4.setOnClickListener {
            if (listJawaban[nomorSoal-1][3] == randomSoal[nomorSoal-1]) nilai += (100.0/ listMateri.size.toDouble()).toInt()
            nextSoal()
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
        if (nomorSoal < listMateri.size) {
            nomorSoal++
            setSoal()
        }
        else {
            val intent = Intent(this@KuisGambarActivity, NilaiActivity::class.java)
            intent.putExtra(NilaiActivity.EXTRA_KATEGORI, kategori)
            intent.putExtra(NilaiActivity.EXTRA_NILAI, nilai)
            startActivity(intent)
        }
    }

    private fun setSoal() {
        binding.tvPerintah.text =
            getString(R.string.perintah, nomorSoal, listMateri[randomSoal[nomorSoal-1]].lowercase())

        setPicture(listJawaban[nomorSoal-1][0], binding.ivJawaban1)
        setPicture(listJawaban[nomorSoal-1][1], binding.ivJawaban2)
        setPicture(listJawaban[nomorSoal-1][2], binding.ivJawaban3)
        setPicture(listJawaban[nomorSoal-1][3], binding.ivJawaban4)
    }

    private fun setPicture(index: Int, imageView: ImageView) {
        val photo = listPhoto.getResourceId(index, -1)
        Glide.with(this)
            .load(photo)
            .apply(RequestOptions().override(600, 600))
            .into(imageView)
    }

    companion object {
        const val EXTRA_MATERI = "extra_materi"
        const val EXTRA_TOPIC = "extra_topic"
    }
}