package ie.por.thirdplace.views.thirdPlaceList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.por.thirdplace.views.thirdPlaceMap.ThirdPlaceMapView
import ie.por.thirdplace.views.thirdPlace.ThirdPlaceView
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel

class ThirdPlaceListPresenter(val view: ThirdPlaceListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int = 0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getThirdPlaces() = app.thirdPlaces.findAll()

    fun doAddThirdPlace() {
        val launcherIntent = Intent(view, ThirdPlaceView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditThirdPlace(thirdPlace: ThirdPlaceModel, pos: Int) {
        val launcherIntent = Intent(view, ThirdPlaceView::class.java)
        launcherIntent.putExtra("thirdPlace_edit", thirdPlace)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowThirdPlacesMap() {
        val launcherIntent = Intent(view, ThirdPlaceMapView::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting  
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}
