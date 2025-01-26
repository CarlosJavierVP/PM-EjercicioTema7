package com.example.ejt7.models

enum class Ciudad(val nombre: String) {
    Malaga("Malaga"), Sevilla("Sevilla"), Cordoba("Cordoba"),
    Madrid("Madrid"), Valencia("Valencia"), Barcelona("Barcelona"),
    Gijon("Gijon"), Pamplona("Pamplona");


    companion object{
        fun fromString(nombre:String):Ciudad?{
            return values().find { it.nombre.equals(nombre) }
        }
    }
}