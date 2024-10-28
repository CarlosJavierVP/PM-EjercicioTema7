package com.example.ejt7.dataBase

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.example.ejt7.contract.PeliculaContract

abstract class DBOpenHelper private constructor(contet: Context?):
    SQLiteOpenHelper(contet, PeliculaContract.NOMBRE_BD, null, PeliculaContract.VERSION){

}