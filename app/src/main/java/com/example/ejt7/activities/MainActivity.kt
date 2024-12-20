package com.example.ejt7.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.ejt7.R
import com.example.ejt7.adapter.PeliculaAdapter
import com.example.ejt7.dataBase.dao.PeliculaDAO
import com.example.ejt7.databinding.ActivityMainBinding
import com.example.ejt7.models.Pelicula
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var listaPeliculas:List<Pelicula>
    private lateinit var adapter: PeliculaAdapter
    private lateinit var layoutManager: LayoutManager

    private lateinit var intentLaunch:ActivityResultLauncher<Intent>
    private var listaVacia:Boolean=false
    private lateinit var  miDAO:PeliculaDAO

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

        this.title = getString(R.string.pelis)
        miDAO = PeliculaDAO()
        listaPeliculas = miDAO.findAll(this)
        layoutManager = LinearLayoutManager(this)
        binding.rvPeliculas.layoutManager=layoutManager
        this.adapter = PeliculaAdapter(listaPeliculas){pelicula ->
            onItemSelected(pelicula)
        }
        binding.rvPeliculas.adapter = this.adapter
        //manejar los espacios entre los items del RecyclerView
        binding.rvPeliculas.setHasFixedSize(true)
        binding.rvPeliculas.itemAnimator = DefaultItemAnimator()

        //preparando intent para recibir datos
        intentLaunch=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            result:ActivityResult ->
            //habría que actualizar la DB (el método está implementado en el dao) para que persistieran las modificaciones
            if(result.resultCode==RESULT_OK){
                val nuevoTitulo=result.data?.extras?.getString("CAMBIAR_TITULO").toString()
                val posicionPeli = result.data?.extras?.getInt("POSICION_PELI")
                posicionPeli?.let {
                    listaPeliculas[it].title = nuevoTitulo
                    miDAO.update(this,listaPeliculas[it])
                    this.adapter = PeliculaAdapter(listaPeliculas){
                        pelicula -> onItemSelected(pelicula)
                        binding.rvPeliculas.adapter = this.adapter
                    }
                }
            }
        }

        refrescar()
        /*
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener, android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })
         */

        this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finish()
            }
        })

    }

    private fun onItemSelected(movieSelected: Pelicula){
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("ID",movieSelected.id)
        intent.putExtra("TITULO", movieSelected.title)
        intentLaunch.launch(intent)

        //Toast --> para mostrar una pequeña ventana con info
        /*
        Toast.makeText(
            this,
            "Duración: ${pelicula.time} minutos - Año: ${pelicula.year}",
            Toast.LENGTH_LONG
        ).show()

         */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val search = menu?.findItem(R.id.buscador)
        val searchView = search?.actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener,
        android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.limpiar ->{
                snackBarBorrarDialogo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun limpia(){
        miDAO.deleteAll(this)
        listaPeliculas = emptyList()
        adapter.updateList(listaPeliculas)
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
            2 -> {
                detallesPeli(movieSelected, item)
            }
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun detallesPeli(movieSelected: Pelicula, item: MenuItem){
        val intent = Intent(this, ActivityDetalle::class.java)
        intent.putExtra("TITULO", movieSelected.title)
        intent.putExtra("DESCRIPCION", movieSelected.description)
        intent.putExtra("IMAGEN", movieSelected.poster)
        intent.putExtra("DURACION", movieSelected.time)
        intent.putExtra("AÑO", movieSelected.year)
        intent.putExtra("PAIS", movieSelected.country)
        intent.putExtra("POSICION_PELI", item.groupId)
        intentLaunch.launch(intent)
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
                    removeMovie(item, movieSelected)
                }.create()
        alert.show()
    }
    private fun snackBarBorrarDialogo(){
        val dialog =
            AlertDialog.Builder(this).setTitle("Eliminar todas las películas")
                .setMessage("¿Estás seguro de eliminar todas las películas?")
                .setNeutralButton("Cerrar",null)
                .setPositiveButton("Aceptar"){_,_->
                    display("Se han eliminado ${listaPeliculas.size} películas")
                    limpia()
                }.create()
        dialog.show()
    }


    private fun removeMovie(item: MenuItem, pelicula: Pelicula) {
        listaPeliculas = listaPeliculas.filter { it != pelicula }
        miDAO.delete(this,pelicula)
        adapter.updateList(listaPeliculas)
       listaVacia = listaPeliculas.isEmpty()
    }


    private fun display(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun refrescar(){
        binding.swipeL.setOnRefreshListener {
            //listaPeliculas = cargarLista()
            listaPeliculas = miDAO.findAll(this)
            adapter.updateList(listaPeliculas)
            binding.swipeL.isRefreshing = false
        }
    }

    private fun filterList(p0: String?){
        p0?.let { r ->
            var filtrarPeli = listaPeliculas.filter { pelicula ->
                pelicula.title.contains(p0, ignoreCase = true)
            }
            if (filtrarPeli.isEmpty()){
                Toast.makeText(this, "No existe esa película", Toast.LENGTH_SHORT).show()
            }
            adapter.updateList(filtrarPeli)
        }
    }




}