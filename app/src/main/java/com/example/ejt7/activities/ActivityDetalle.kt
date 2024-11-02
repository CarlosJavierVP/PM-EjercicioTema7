package com.example.ejt7.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejt7.R
import com.example.ejt7.databinding.ActivityDetalleBinding

class ActivityDetalle : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //recibir los datos del mainActivity
        val tituloPeli = intent.getStringExtra("TITULO")
        val descripcionPeli = intent.getStringExtra("DESCRIPCION")
        val imagenPeli = intent.getIntExtra("IMAGEN",0)
        val duracionPeli = intent.getIntExtra("DURACION",0)
        val yearPeli = intent.getIntExtra("AÑO",0)
        val paisPeli = intent.getStringExtra("PAIS")

        val titulo = binding.txtTitle
        val descrip = binding.txtDescription
        val imagen = binding.posterDetail
        val duracion = binding.txtDuracion
        val anho = binding.txtYear
        val pais = binding.txtPais

        titulo.text = tituloPeli
        descrip.text = descripcionPeli
        imagen.setImageResource(imagenPeli)
        duracion.text = "Duración: $duracionPeli minutos"
        anho.text = "Año: $yearPeli"
        pais.text = "Nacionalidad: $paisPeli"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.limpiar ->{
                volverAtras()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun volverAtras(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}