package com.example.ejt7.dataBase.dao

import android.content.Context
import android.database.Cursor
import com.example.ejt7.dataBase.DBOpenHelper
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad

class CineDAO: DAO<Cine> {

    companion object{
        private const val SELECT_ALl_CINEMA = "SELECT * FROM Cine"

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

    override fun delete(context: Context?, t: Cine) {
        TODO("Not yet implemented")
    }

    override fun update(context: Context?, t: Cine) {
        TODO("Not yet implemented")
    }

    override fun save(context: Context?, t: Cine) {
        TODO("Not yet implemented")
    }
}