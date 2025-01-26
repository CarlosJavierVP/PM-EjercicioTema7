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
                listaCines.add(Cine(c.getInt(0),c.getString(1), Ciudad.valueOf(c.getString(2)),c.getDouble(3), c.getDouble(4)))
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c.close()
        }
        return listaCines
    }

    override fun delete(context: Context?, t: Int) {
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

    //MODIFICAR DAO --> ARREGLAR MÉTODOS PARA OBTENER LOS DATOS DE LA DB Y PODER PINTARLOS
    fun findByMovie(context: Context?, peli:Pelicula):List<Cine>{
        val listaCines: MutableList<Cine> = mutableListOf()
        lateinit var c: Cursor
        //lateinit var c1: Cursor
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            /*
            val columnas = arrayOf(
                PeliculaCineContract.Companion.EntradaPeli.IDCOL,
                PeliculaCineContract.Companion.EntradaPeli.TITULOCOL,
                PeliculaCineContract.Companion.EntradaPeli.DESCRIPCIONCOLC,
                PeliculaCineContract.Companion.EntradaPeli.POSTERCOL,
                PeliculaCineContract.Companion.EntradaPeli.TIMECOL,
                PeliculaCineContract.Companion.EntradaPeli.YEARCOL,
                PeliculaCineContract.Companion.EntradaPeli.COUNTRYCOL
            )

         */
            c = db.rawQuery("SELECT * FROM Cine c INNER JOIN Relacion r ON c.id = r.id_cine INNER JOIN Pelicula p ON r.id_peli = p.id", arrayOf(peli.id.toString()))
            //c1 = db.query(PeliculaCineContract.Companion.EntradaCine.TABLA, columnas, null, null,null,null,null)
            while(c.moveToNext()){
                val cinema = Cine(c.getInt(0),c.getString(1), Ciudad.valueOf(c.getString(2)),c.getDouble(3), c.getDouble(4))
                listaCines.add(cinema)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            c.close()
        }
        return listaCines

    }


    fun findById(context: Context?, id:Int):Cine{
        lateinit var res:Cine
        lateinit var db:SQLiteDatabase
        lateinit var c: Cursor
        try {
            db = DBOpenHelper.getInstance(context)!!.readableDatabase
            c = db.rawQuery(SELECT_CINE_ID, arrayOf(id.toString()))
            if(c.moveToNext()){
                res = Cine(
                    c.getInt(0),
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