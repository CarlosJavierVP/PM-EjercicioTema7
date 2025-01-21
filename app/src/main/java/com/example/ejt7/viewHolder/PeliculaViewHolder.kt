package com.example.ejt7.viewHolder

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejt7.databinding.ItemPeliculaBinding
import com.example.ejt7.models.Pelicula

class PeliculaViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    private val binding = ItemPeliculaBinding.bind(view)
    private lateinit var peli: Pelicula

    fun render(item: Pelicula, onClickListener: (Pelicula) -> Unit){
        peli = item
        binding.tvTitle.text=item.title
        Glide.with(binding.ivPoster.context).load(item.poster).fitCenter().into(binding.ivPoster)
        itemView.setOnClickListener{
            onClickListener(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }


    override fun onCreateContextMenu(
        p0: ContextMenu?,
        p1: View?,
        p2: ContextMenu.ContextMenuInfo?
    ) {
        p0!!.setHeaderTitle(peli.title)
        p0.add(this.adapterPosition, 0, 0, "Eliminar")
        p0.add(this.adapterPosition, 1,1,"Editar")
        p0.add(this.adapterPosition, 2,2, "Detalle")
    }


}