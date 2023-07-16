package com.example.aplikasisenter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.aplikasisenter.databinding.ActivityTentangBinding

class TentangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTentangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTentangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("Tentang")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.tvContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("ibangproject1@gmail.com")
                ) // Ganti dengan alamat email tujuan
                putExtra(Intent.EXTRA_SUBJECT, "Aplikasi SenterinAja") // Ganti dengan subjek email
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Hai, Saya ingin konsultasi terkait aplikasi SentrinAja."
                ) // Ganti dengan isi email
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.d("Intent", "Tidak ada aplikasi email yang dapat menangani intent ini")
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish() // Menggunakan metode onBackPressed() untuk kembali ke halaman sebelumnya
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}