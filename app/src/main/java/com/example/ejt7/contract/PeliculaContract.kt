package com.example.ejt7.contract

import android.provider.BaseColumns

class PeliculaContract {
    companion object{
        val NOMBRE_BD = "dbpeliculas"
        val VERSION = 1
        class Entrada: BaseColumns{
            companion object{
                val TABLA = "peliculas"
                val IDCOL = "id"
                val TITULOCOL = "titulo"
                val DESCRIPCIONCOLC = "descripcion"
                val POSTERCOL = "poster"
                val TIMECOL = "duracion"
                val YEARCOL = "anho"
                val COUNTRYCOL = "pais"
            }

        }
    }
}