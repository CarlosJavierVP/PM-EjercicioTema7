package com.example.ejt7.dataBase.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad
import com.example.ejt7.models.Pelicula

class PeliculaDAO():DAO<Pelicula> {
    override fun findAll(context: Context?): MutableList<Pelicula> {
        var lista: MutableList<Pelicula>
        lateinit var c: Cursor

        try{
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM Pelicula"
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

    override fun update(context: Context?, pelicula:Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val q = db.compileStatement("UPDATE Pelicula SET titulo=? WHERE id=${pelicula.id};")
        q.bindString(1,pelicula.title)
        q.executeUpdateDelete()
        q.close()
        db.close()
    }

    override fun save(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "INSERT INTO Pelicula (titulo, descripcion, poster, duracion, anho, pais) VALUES "
                    +" ('${pelicula.title}', '${pelicula.description}', '${pelicula.poster}', "
                    +" '${pelicula.time}', '${pelicula.year}', '${pelicula.country}');"
        )
        db.close()
    }

    override fun delete(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "DELETE FROM Pelicula WHERE id=${pelicula.id};"
        )
        db.close()
    }

    fun deleteAll(context: Context?){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL("DELETE FROM Pelicula;")
        db.close()
    }

    fun findMovieById(context: Context?, id:Int): Pelicula{
       lateinit var res: Pelicula
       lateinit var db: SQLiteDatabase
       lateinit var c: Cursor
       try {
           db = DBOpenHelper.getInstance(context)!!.readableDatabase
           c = db.rawQuery("SELECT * FROM Pelicula WHERE id = ?",null)
           if(c.moveToNext()){
               res = Pelicula(
                   c.getInt(0),
                   c.getString(1),
                   c.getString(2),
                   c.getInt(3),
                   c.getInt(4),
                   c.getInt(5),
                   c.getString(6)
               )
           }
       }catch (e: Exception){
           e.printStackTrace()
       }finally {
           c.close()
           db.close()
       }
        return res
    }



}