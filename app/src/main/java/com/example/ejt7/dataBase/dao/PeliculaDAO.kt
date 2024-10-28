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
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
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

    fun actualizarBBDD(context: Context?, pelicula:Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "UPDATE peliculas "
                    +"SET titulo='${pelicula.title}'"
                    +"SET descripcion='${pelicula.description}'"
                    +"SET duracion='${pelicula.time}'"
                    +"SET anho='${pelicula.year}'"
                    +"SET pais='${pelicula.country}'"
                    +"WHERE id=${pelicula.id};"
        )
        db.close()
    }

    fun insertarBBDD(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "INSERT INTO peliculas (titulo, descripcion, poster, duracion, anho, pais) VALUES "
                    +" ('${pelicula.title}', '${pelicula.description}', '${pelicula.poster}', "
                    +" '${pelicula.time}', '${pelicula.year}', '${pelicula.country}');"
        )
        db.close()
    }

    fun eliminar(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "DELETE FROM peliculas WHERE id=${pelicula.id};"
        )
        db.close()
    }

}