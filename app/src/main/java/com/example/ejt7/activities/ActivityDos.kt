package com.example.ejt7.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejt7.R
import com.example.ejt7.databinding.ActivityDosBinding

class ActivityDos : AppCompatActivity() {

    private lateinit var binding: ActivityDosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //recibir datos de MainActivity
        val tituloPeli = intent.getStringExtra("CAMBIAR_TITULO")
        val imgMovie= intent.getIntExtra("IMAGEN_PELI",0)
        val posicionPeli = intent.getIntExtra("POSICION_PELI", 0)

        val nuevoTitulo = binding.editText
        val img = binding.imageView

        val btnCambiar= findViewById<Button>(R.id.btnCambiar)
        val btnVolver = findViewById<Button>(R.id.btnCancelar)

        nuevoTitulo.hint=tituloPeli
        if(imgMovie != 0){
            img.setImageResource(imgMovie)
        }

        //botón para cambiar el nombre del título y devolverlo a la MainActivity
        btnCambiar.setOnClickListener{
            enviarDatos(nuevoTitulo, posicionPeli)
        }

        btnVolver.setOnClickListener{
            cancelar()
        }

    }

    private fun cancelar() {
        val intent = Intent()
        setResult(RESULT_CANCELED, intent)
        finish()
    }

    private fun enviarDatos(nuevoTitulo: EditText, posicionPeli: Int) {
        val intent = Intent()
        val tituloModificado = nuevoTitulo.text.toString()
        if (tituloModificado.isNotBlank()){
            intent.putExtra("CAMBIAR_TITULO", tituloModificado)
            intent.putExtra("POSICION_PELI", posicionPeli)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}