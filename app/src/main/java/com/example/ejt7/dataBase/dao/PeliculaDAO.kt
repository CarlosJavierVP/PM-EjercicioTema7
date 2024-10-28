package com.example.ejt7.dataBase.dao

import android.content.Context
import android.database.Cursor
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Pelicula

class PeliculaDAO {
    fun cargarLista(context: Context):MutableList<Pelicula>{
        var lista = mutableListOf<Pelicula>()
        lateinit var c: Cursor

        try{
            val db = DBOpenHelper.getInstance(context)!!.writableDatabase
            val sql = "SELECT * FROM peliculas"
            c = db.rawQuery(sql,null)
            lista = mutableListOf()

            while (c.moveToNext()){
                val nueva = Pelicula(c.getInt(0),c.getString(1), c.getString(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getString(6))
                lista.add(nueva)
            }

        }finally {
            c.close()
        }
        return lista
    }
}