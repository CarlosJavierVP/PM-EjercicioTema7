package com.example.ejt7.dataBase.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad

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