package ie.por.thirdplace.views.editLocation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.models.thirdplace.Location
import ie.por.thirdplace.helpers.*

class EditLocationView : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, OnMapLongClickListener {

    private lateinit var map: GoogleMap
    lateinit var presenter: EditLocationPresenter
    private var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        AuthHelper.checkLogin(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        presenter = EditLocationPresenter(this)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onBackPressedDispatcher.addCallback(this) {
            presenter.doOnBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        presenter.initMap(map)
        map.setOnMapLongClickListener(this)
    }

    override fun onMarkerDragStart(marker: Marker) {

    }

    override fun onMarkerDrag(marker: Marker) {

    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateMarker(marker)
    }

    override fun onMapLongClick(latLng: LatLng) {

        val markerOptions = MarkerOptions()
            .title("Updated location")
            .snippet("GPS : $latLng.latitude, $latLng.longitude")
            .draggable(true)
            .position(latLng)

        map.clear()
        map.addMarker(markerOptions)

        presenter.doUpdateLocation(latLng.latitude, latLng.longitude, map.cameraPosition.zoom)

        Snackbar.make(findViewById(R.id.activity_map_root), R.string.success_locationUpdate, Snackbar.LENGTH_LONG).show()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }
}
