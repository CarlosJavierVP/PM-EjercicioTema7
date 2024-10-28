package com.example.ejt7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejt7.models.Pelicula

class PeliculaAdapter(private val listaPeli: List<Pelicula>,
                      private val onClickListener: (Pelicula)->Unit) : RecyclerView.Adapter<PeliculaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val layaoutInflater = LayoutInflater.from(parent.context)
        return PeliculaViewHolder(layaoutInflater.inflate(R.layout.item_pelicula, parent, false))
    }

    override fun getItemCount(): Int {
        return listaPeli.size
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        val item = listaPeli[position]
        holder.render(item, onClickListener)
    }


}