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

        val idPeli = intent.getLongExtra("ID",0)
        //val listaCinemas: ArrayList<Cine>? = intent.getParcelableArrayListExtra("listaCinemas")
        daoPeli = PeliculaDAO()
        daoCine = CineDAO()

        val peli = daoPeli.findMovieById(this,idPeli)

        val cines = daoCine.findCinesByPelis(this,peli)


        val items : ArrayList<OverlayItem> = ArrayList<OverlayItem>()
        cines.forEach { c: Cine ->
            items.add(
                OverlayItem(
                    c.ciudad.toString(),
                    c.nombreCine+" - "+peli.title,
                    GeoPoint(c.latitud, c.longitud)
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

        }
        /*
        items.add(
            OverlayItem(
                "CESUR Málaga Este",
                "Centro privado de FP",
                GeoPoint(36.7194937132025, -4.365499010622804)
            )
        )

         */




    }
    private fun addMarker(cine: Cine){
        val marker = Marker(map)
        marker.position = GeoPoint(cine.latitud, cine.longitud)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        //val info: String = cine.ciudad.toString()+" '\n' "+cine.nombreCine + " - "//+peli.title
        marker.title = cine.ciudad.toString()+" '\n' "+cine.nombreCine
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