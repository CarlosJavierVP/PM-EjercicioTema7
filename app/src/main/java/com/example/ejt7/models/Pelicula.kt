package com.example.ejt7.models

import java.io.Serializable

class Pelicula (var id:Int,var title:String,var description:String, var poster:Int, var time:Int, var year:Int, var country:String, var cines: Cine):Serializable {
}