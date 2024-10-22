package ie.por.thirdplace.views.thirdPlaceMap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import ie.por.thirdplace.databinding.ActivityThirdplaceMapsBinding
import ie.por.thirdplace.databinding.ContentThirdplaceMapsBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.thirdplace.ThirdPlaceModel

class ThirdPlaceMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityThirdplaceMapsBinding
    private lateinit var contentBinding: ContentThirdplaceMapsBinding
    lateinit var presenter: ThirdPlaceMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        binding = ActivityThirdplaceMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentThirdplaceMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }

    }

    fun showThirdPlace(thirdPlace: ThirdPlaceModel) {
        contentBinding.currentTitle.text = thirdPlace.title
        contentBinding.currentDescription.text = thirdPlace.description
        Picasso.get()
            .load(thirdPlace.image)
            .into(contentBinding.currentImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
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
}