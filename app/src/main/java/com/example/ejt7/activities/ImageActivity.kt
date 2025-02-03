package com.example.ejt7.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.example.ejt7.R
import com.example.ejt7.dataBase.dao.PeliculaDAO
import com.example.ejt7.databinding.ActivityImageBinding

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.extras!!.getLong("id")
        val miDao = PeliculaDAO()
        val peli = miDao.findMovieById(this, id)
        if (peli.uri.isNotEmpty()){
            val uri = Uri.parse(peli.uri)
            binding.wholeImage.load(uri) //load método de librería coil para visualizar imágenes en el activity
        } else {
            Toast.makeText(this, "${peli.title} no tiene foto asociada", Toast.LENGTH_SHORT).show()
        }

    }
}