package ie.por.thirdplace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.por.thirdplace.databinding.ActivityThirdplaceMapsBinding
import ie.por.thirdplace.databinding.ContentThirdplaceMapsBinding
import ie.por.thirdplace.main.MainApp

class ThirdplaceMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityThirdplaceMapsBinding
    private lateinit var contentBinding: ContentThirdplaceMapsBinding
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityThirdplaceMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentThirdplaceMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.thirdPlaces.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }

override fun onDestroy() {
    super.onDestroy()
    contentBinding.mapView.onDestroy()
}

override fun onLowMemory() {
    super.onLowMemory()
    contentBinding.mapView.onLowMemory()
}

override fun onPause() {
    super.onPause()
    contentBinding.mapView.onPause()
}

override fun onResume() {
    super.onResume()
    contentBinding.mapView.onResume()
}

override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    contentBinding.mapView.onSaveInstanceState(outState)
}

    override fun onMarkerClick(marker: Marker): Boolean {
        //val placemark = marker.tag as PlacemarkModel
        val tag = marker.tag as Long
        val thirdPlace = app.thirdPlaces.findById(tag)
        contentBinding.currentTitle.text = thirdPlace!!.title
        contentBinding.currentDescription.text = thirdPlace.description
        Picasso.get().load(thirdPlace.image).into(contentBinding.currentImage)
        return false
    }
}