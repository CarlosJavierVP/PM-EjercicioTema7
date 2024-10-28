package com.example.ejt7

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ejt7.databinding.ActivityMainBinding
import com.example.ejt7.models.Pelicula
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var listaPeliculas:MutableList<Pelicula>
    private lateinit var adapter: PeliculaAdapter
    private lateinit var layoutManager: LayoutManager

    private lateinit var intentLaunch:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaPeliculas = cargarLista()
        layoutManager = LinearLayoutManager(this)
        binding.rvPeliculas.layoutManager=layoutManager
        adapter = PeliculaAdapter(listaPeliculas){pelicula ->
            onItemSelected(pelicula)
        }
        binding.rvPeliculas.adapter = adapter
        //manejar los espacios entre los items del RecyclerView
        binding.rvPeliculas.setHasFixedSize(true)
        binding.rvPeliculas.itemAnimator = DefaultItemAnimator()

        //preparando intent para recibir datos
        intentLaunch=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            result:ActivityResult ->
            if(result.resultCode==RESULT_OK){
                val nuevoTitulo=result.data?.extras?.getString("CAMBIAR_TITULO").toString()
                val posicionPeli = result.data?.extras?.getInt("POSICION_PELI")
                posicionPeli?.let {
                    listaPeliculas[it].title = nuevoTitulo
                    this.adapter = PeliculaAdapter(listaPeliculas){
                        pelicula -> onItemSelected(pelicula)
                    }
                    binding.rvPeliculas.adapter = this.adapter
                }
            }
        }

        refrescar()

    }

    private fun cargarLista():MutableList<Pelicula>{
        val lista = mutableListOf<Pelicula>()
        for (pelicula in PeliculaProvider.listaCarga){
            lista.add(pelicula)
        }
        return lista
    }

    private fun onItemSelected(pelicula: Pelicula){
        Toast.makeText(
            this,
            "Duración: ${pelicula.time} minutos - Año: ${pelicula.year}",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.limpiar ->{
                limpia()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun limpia(){
        listaPeliculas.clear()
        this.adapter.notifyItemRangeRemoved(0,listaPeliculas.size)
        binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){ pelicula ->
            onItemSelected(pelicula)
        }
    }

/*
    private fun recarga(){
        listaPeliculas = cargarLista()
        adapter.notifyItemRangeInserted(0,listaPeliculas.size)
        binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){ pelicula ->
            onItemSelected(pelicula)
        }
    }

 */


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val movieSelected: Pelicula = listaPeliculas[item.groupId]


        when (item.itemId) {
            0 -> {
                snackBarBorrar(movieSelected, item)
            }
            1 -> {
                enviarDatos(movieSelected, item)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun enviarDatos(movieSelected: Pelicula, item:MenuItem) {
        val intent = Intent(this, ActivityDos::class.java)
        intent.putExtra("IMAGEN_PELI", movieSelected.poster)
        intent.putExtra("CAMBIAR_TITULO", movieSelected.title)
        intent.putExtra("POSICION_PELI",item.groupId)
        intentLaunch.launch(intent)
    }

    private fun snackBarBorrar(
        movieSelected: Pelicula,
        item: MenuItem
    ) {
        val alert =
            AlertDialog.Builder(this).setTitle("Eliminar ${movieSelected.title}")
                .setMessage("¿Estás seguro de que quieres eliminar ${movieSelected.title}?")
                .setNeutralButton("Cerrar", null)
                .setPositiveButton(
                    "Aceptar"
                ) { _, _ ->
                    display("Se ha eliminado ${movieSelected.title}")
                    listaPeliculas.removeAt(item.groupId)
                    adapter.notifyItemRemoved(item.groupId)
                    adapter.notifyItemRangeChanged(item.groupId, listaPeliculas.size)
                    binding.rvPeliculas.adapter =
                        PeliculaAdapter(listaPeliculas) { pelicula ->
                            onItemSelected(pelicula)
                        }
                }.create()
        alert.show()
    }


    private fun display(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun refrescar(){
        binding.swipeL.setOnRefreshListener {
            listaPeliculas = cargarLista()
            adapter.notifyItemRangeInserted(0,listaPeliculas.size-1)
            binding.rvPeliculas.adapter = PeliculaAdapter(listaPeliculas){ pelicula ->
                onItemSelected(pelicula)
            }
            binding.swipeL.isRefreshing = false
        }
    }



}