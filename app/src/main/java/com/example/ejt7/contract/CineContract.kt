package com.example.ejt7.contract

import android.provider.BaseColumns
import com.example.ejt7.models.Ciudad

class CineContract {
    class Entrada: BaseColumns{
        companion object {
            val TABLA = "Cine"
            val ID = "id"
            val NOMBRE = "nombre"
            val CIUDAD = "ciudad"
            val LATITUD = "latitud"
            val LONGITUD = "longitud"
        }
    }
}