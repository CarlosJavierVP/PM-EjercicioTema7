package com.example.ejt7.contract

import android.provider.BaseColumns

class PeliculaCineContract {

    companion object{
        val NOMBRE_BD = "db_peliculas_cine"
        val VERSION = 1
        class EntradaPeli: BaseColumns {
            companion object{
                val TABLA = "Pelicula"
                val IDCOL = "id"
                val TITULOCOL = "titulo"
                val DESCRIPCIONCOLC = "descripcion"
                val POSTERCOL = "poster"
                val TIMECOL = "duracion"
                val YEARCOL = "anho"
                val COUNTRYCOL = "pais"
            }

        }

        class EntradaCine: BaseColumns{
            companion object {
                val TABLA = "Cine"
                val ID = "id"
                val NOMBRE = "nombre"
                val CIUDAD = "ciudad"
                val LATITUD = "latitud"
                val LONGITUD = "longitud"
            }
        }

        class EntradaRelacion: BaseColumns {
            companion object {
                val TABLA = "Relacion"
                val ID = "id"
                val ID_CINE = "id_cine"
                val ID_PELI = "id_peli"
            }
        }
    }
}