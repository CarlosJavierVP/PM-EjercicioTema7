package com.example.ejt7.dataBase.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.ejt7.contract.PeliculaCineContract
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad
import com.example.ejt7.models.Pelicula

class CineDAO: DAO<Cine> {

    companion object{
        private const val SELECT_ALl_CINEMA = "SELECT * FROM Cine"

        private const val SELECT_PELI_CINE_RELACION = "SELECT id_cine FROM Relacion WHERE id_peli = ?"

        private const val SELECT_CINE_ID = "SELECT * FROM Cine WHERE id = ?"
    }



    override fun findAll(context: Context?): List<Cine> {
        val listaCines: MutableList<Cine> = mutableListOf()
        lateinit var c: Cursor

        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_ALl_CINEMA, null)
            while(c.moveToNext()){
                listaCines.add(Cine(c.getLong(0),c.getString(1), Ciudad.valueOf(c.getString(2)),c.getDouble(3), c.getDouble(4)))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c.close()
        }
        return listaCines
    }

    override fun delete(context: Context?, t: Long) {
        TODO("Not yet implemented")
    }

    override fun update(context: Context?, t: Cine) {
        TODO("Not yet implemented")
    }

    override fun save(context: Context?, t: Cine) {
        TODO("Not yet implemented")
    }

    fun PeliCine(context: Context?, id: Int): List<Int>{
        val listaRelacion: MutableList<Int> = mutableListOf()
        lateinit var db: SQLiteDatabase
        lateinit var c: Cursor
        try{
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_PELI_CINE_RELACION, arrayOf(id.toString()))
            while (c.moveToNext()){
                listaRelacion.add(c.getInt(1))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c.close()
            db.close()
        }
        return listaRelacion
    }


    fun findByMovie(context: Context?, peli:Pelicula):List<Cine>{
        val listaCines: MutableList<Cine> = mutableListOf()
        var db: SQLiteDatabase? = null
        var c: Cursor? = null
        try {
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db?.rawQuery("SELECT c.nombreCine, c.ciudad, c.latitud, c.longitud FROM Cine c INNER JOIN Relacion r ON c.id = r.id_cine WHERE r.id_peli = ?", arrayOf(peli.id.toString()))
            if(c != null){
                while(c.moveToNext()){
                    val cinema = Cine(c.getLong(0),c.getString(1), Ciudad.valueOf(c.getString(2)),c.getDouble(3), c.getDouble(4))
                    listaCines.add(cinema)
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c?.close()
        }
        return listaCines

    }
/*
    fun listaCinesPorPeli(context: Context?, idPelicula: Long): ArrayList<Cine>{
        val listaCines: ArrayList<Cine> = ArrayList<Cine>()

        val db: SQLiteDatabase = DBOpenHelper.getInstance(context)!!.readableDatabase
        val query = "SELECT ${PeliculaCineContract.Companion.EntradaCine.ID}, \n" +
                "${PeliculaCineContract.Companion.EntradaCine.NOMBRE},\n" +
                "${PeliculaCineContract.Companion.EntradaCine.CIUDAD},\n" +
                "${PeliculaCineContract.Companion.EntradaCine.LATITUD},\n" +
                "${PeliculaCineContract.Companion.EntradaCine.LONGITUD}\n" +
                "FROM ${PeliculaCineContract.Companion.EntradaCine.TABLA}\n" +
                "INNER JOIN ${PeliculaCineContract.Companion.EntradaRelacion.TABLA}\n" +
                "ON ${PeliculaCineContract.Companion.EntradaCine.ID} = ${PeliculaCineContract.Companion.EntradaRelacion.ID_CINE}\n" +
                "WHERE ${PeliculaCineContract.Companion.EntradaRelacion.ID_PELI} = ?"
        val cursor = db.rawQuery(query, arrayOf(idPelicula.toString()))

        if(cursor.moveToFirst()){
            do{
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(PeliculaCineContract.Companion.EntradaCine.ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(PeliculaCineContract.Companion.EntradaCine.NOMBRE))
                val ciudadString = cursor.getString(cursor.getColumnIndexOrThrow(PeliculaCineContract.Companion.EntradaCine.CIUDAD))
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(PeliculaCineContract.Companion.EntradaCine.LATITUD))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(PeliculaCineContract.Companion.EntradaCine.LONGITUD))

                val ciudad= Ciudad.fromString(ciudadString) ?: throw IllegalArgumentException("Ciudad no válida")

                val cine = Cine(id,nombre,ciudad,latitud,longitud)
                listaCines.add(cine)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return listaCines
    }

 */



    fun findById(context: Context?, id:Int):Cine{
        lateinit var res:Cine
        lateinit var db:SQLiteDatabase
        lateinit var c: Cursor
        try {
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_CINE_ID, arrayOf(id.toString()))
            if(c.moveToNext()){
                res = Cine(
                    c.getLong(0),
                    c.getString(1),
                    Ciudad.valueOf(c.getString(2)),
                    c.getDouble(3),
                    c.getDouble(4)
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