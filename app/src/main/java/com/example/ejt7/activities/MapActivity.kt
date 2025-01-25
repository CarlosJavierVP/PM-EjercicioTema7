package com.example.ejt7.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejt7.R
import com.example.ejt7.dataBase.dao.CineDAO
import com.example.ejt7.dataBase.dao.PeliculaDAO
import com.example.ejt7.databinding.ActivityMapBinding
import com.example.ejt7.models.Cine
import com.example.ejt7.models.Ciudad
import com.example.ejt7.models.Pelicula
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private lateinit var binding: ActivityMapBinding

    private lateinit var daoCine: CineDAO
    private lateinit var daoPeli: PeliculaDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, this.getPreferences(Context.MODE_PRIVATE))
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        map = binding.mapOSM
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(9.5)


        val idPeli = intent.getIntExtra("ID",0)
        //val tituloPeli = intent.getStringExtra("TITULO")
        val peli = daoPeli.findMovieById(this, idPeli)

        val cines: List<Int> = daoCine.PeliCine(this, idPeli)

        /*
        cines.forEach { cineId ->
            var cinema = daoCine.findById(this, cineId);
            addMarker(cinema)
        }

         */
        val items : ArrayList<OverlayItem> = ArrayList<OverlayItem>()
        cines.forEach { id ->
            val cine = daoCine.findById(this,id)
            val latitudLongitud = GeoPoint(cine.latitud, cine.longitud)
            val marker = Marker(map)
            val info: String = cine.ciudad.toString()
            marker.title = info
            items.add(
                OverlayItem(
                    marker.title,
                    cine.nombreCine+" - "+peli.title,
                    latitudLongitud
                )
            )
        }

        val mOverlay:ItemizedOverlayWithFocus<OverlayItem> = ItemizedOverlayWithFocus<OverlayItem>(
            items,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }, applicationContext
        )
        mOverlay.setFocusItemsOnTap(true)
        map.getOverlays().add(mOverlay)
        mapController.setCenter(GeoPoint(36.7194937132025, -4.365499019622804))


        /*
        val items : ArrayList<OverlayItem> = ArrayList<OverlayItem>()
        items.add(

            OverlayItem(
                "",
                "Centro privado de FP",
                GeoPoint(36.7194937132025, -4.365499010622804)
            )
        )

        val mOverlay:ItemizedOverlayWithFocus<OverlayItem> = ItemizedOverlayWithFocus<OverlayItem>(
            items,
            object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }, applicationContext
        )
        mOverlay.setFocusItemsOnTap(true)
        map.getOverlays().add(mOverlay)
        mapController.setCenter(GeoPoint(36.7194937132025, -4.365499019622804))

         */


    }
    private fun addMarker(cine: Cine){
        val latitudLongitud = GeoPoint(cine.latitud, cine.longitud)
        val marker = Marker(map)
        marker.position = latitudLongitud
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        val info: String = cine.ciudad.toString()+" '\n' "+cine.nombreCine + " - "//+peli.title
        marker.title = info
        //marker.icon = ContextCompat.getDrawable(this, peli.poster)
        map.overlays.add(marker)

    }

    public override fun onResume(){
        super.onResume()
        map.onResume()
    }

    public override fun onPause() {
        super.onPause()
        map.onPause()
    }

}