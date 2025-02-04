package com.example.ejt7.dataBase.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.ejt7.contract.PeliculaCineContract
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad
import com.example.ejt7.models.Pelicula

class PeliculaDAO():DAO<Pelicula> {

    override fun findAll(context: Context?): MutableList<Pelicula> {
        lateinit var lista: MutableList<Pelicula>
        lateinit var c: Cursor

        try{
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val columnas = arrayOf(
                PeliculaCineContract.Companion.EntradaPeli.IDCOL,
                PeliculaCineContract.Companion.EntradaPeli.TITULOCOL,
                PeliculaCineContract.Companion.EntradaPeli.DESCRIPCIONCOL,
                PeliculaCineContract.Companion.EntradaPeli.POSTERCOL,
                PeliculaCineContract.Companion.EntradaPeli.TIMECOL,
                PeliculaCineContract.Companion.EntradaPeli.YEARCOL,
                PeliculaCineContract.Companion.EntradaPeli.COUNTRYCOL,
                PeliculaCineContract.Companion.EntradaPeli.URICOL
            )
            c = db.query(PeliculaCineContract.Companion.EntradaPeli.TABLA,
                columnas, null, null, null, null, null)

            lista = mutableListOf()

            while (c.moveToNext()){
                val nueva = Pelicula(c.getLong(0),c.getString(1), c.getString(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getString(6), c.getString(7))
                lista.add(nueva)
            }

        }finally {
            c.close()
        }
        return lista
    }


/*
    override fun findAll(context: Context?): MutableList<Pelicula> {
        var lista: MutableList<Pelicula>
        lateinit var c: Cursor

        try{
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM Pelicula"
            c = db.rawQuery(sql,null)
            lista = mutableListOf()

            while (c.moveToNext()){
                val nueva = Pelicula(c.getLong(0),c.getString(1), c.getString(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getString(6), c.getString(7))
                lista.add(nueva)
            }

        }finally {
            c.close()
        }
        return lista
    }

 */

    override fun update(context: Context?, pelicula:Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val q = db.compileStatement("UPDATE Pelicula SET titulo=? WHERE id=${pelicula.id};")
        q.bindString(1,pelicula.title)
        q.executeUpdateDelete()
        q.close()
        db.close()
    }



    fun actualizarBBDD(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        val values = ContentValues()
        values.put(PeliculaCineContract.Companion.EntradaPeli.IDCOL, pelicula.id)
        values.put(PeliculaCineContract.Companion.EntradaPeli.TITULOCOL, pelicula.title)
        values.put(PeliculaCineContract.Companion.EntradaPeli.DESCRIPCIONCOL, pelicula.description)
        values.put(PeliculaCineContract.Companion.EntradaPeli.POSTERCOL, pelicula.poster)
        values.put(PeliculaCineContract.Companion.EntradaPeli.TIMECOL, pelicula.time)
        values.put(PeliculaCineContract.Companion.EntradaPeli.YEARCOL, pelicula.year)
        values.put(PeliculaCineContract.Companion.EntradaPeli.COUNTRYCOL, pelicula.country)
        values.put(PeliculaCineContract.Companion.EntradaPeli.URICOL, pelicula.uri)
        db.update(PeliculaCineContract.Companion.EntradaPeli.TABLA, values, "id=?", arrayOf(pelicula.id.toString()))
        db.close()
    }

    override fun save(context: Context?, pelicula: Pelicula){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "INSERT INTO Pelicula (titulo, descripcion, poster, duracion, anho, pais, uri) VALUES "
                    +" ('${pelicula.title}', '${pelicula.description}', '${pelicula.poster}', "
                    +" '${pelicula.time}', '${pelicula.year}', '${pelicula.country}','${pelicula.uri}');"
        )
        db.close()
    }

    override fun delete(context: Context?, idpelicula: Long){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "DELETE FROM Pelicula WHERE id=${idpelicula};"
        )
        db.close()
    }

    fun deleteAll(context: Context?){
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL("DELETE FROM Pelicula;")
        db.close()
    }
    fun findMovieById(context: Context?, id:Long): Pelicula{
        lateinit var res: Pelicula
        //lateinit var db: SQLiteDatabase
        lateinit var c: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val columnas = arrayOf(
                PeliculaCineContract.Companion.EntradaPeli.IDCOL,
                PeliculaCineContract.Companion.EntradaPeli.TITULOCOL,
                PeliculaCineContract.Companion.EntradaPeli.DESCRIPCIONCOL,
                PeliculaCineContract.Companion.EntradaPeli.POSTERCOL,
                PeliculaCineContract.Companion.EntradaPeli.TIMECOL,
                PeliculaCineContract.Companion.EntradaPeli.YEARCOL,
                PeliculaCineContract.Companion.EntradaPeli.COUNTRYCOL,
                PeliculaCineContract.Companion.EntradaPeli.URICOL
            )
            val identificador = id.toString()
            val valores = arrayOf(identificador)
            c = db.query(
                PeliculaCineContract.Companion.EntradaPeli.TABLA,
                columnas, "id=?", valores, null, null, null)
            if(c.moveToNext()){
                res = Pelicula(
                    c.getLong(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7)
                )
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c.close()
            //db.close()
        }
        return res
    }
    /*
    fun findMovieById(context: Context?, id:Long): Pelicula{
       lateinit var res: Pelicula
       lateinit var db: SQLiteDatabase
       lateinit var c: Cursor
       try {
           db = DBOpenHelper.getInstance(context)!!.readableDatabase
           c = db.rawQuery("SELECT * FROM Pelicula WHERE id = ?", arrayOf(id.toString()))
           if(c.moveToNext()){
               res = Pelicula(
                   c.getLong(0),
                   c.getString(1),
                   c.getString(2),
                   c.getInt(3),
                   c.getInt(4),
                   c.getInt(5),
                   c.getString(6),
                   c.getString(7)
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
     */



}