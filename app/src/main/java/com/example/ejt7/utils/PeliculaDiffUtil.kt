package com.example.ejt7.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.ejt7.models.Pelicula

class PeliculaDiffUtil (private val oldList:List<Pelicula>,
                        private val newList: List<Pelicula>): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}