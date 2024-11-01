package ie.por.thirdplace.views.thirdPlace

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.thirdplace.ThirdPlaceModel
import ie.por.thirdplace.models.thirdplace.Location
import ie.por.thirdplace.views.editLocation.EditLocationView
import timber.log.Timber

class ThirdPlacePresenter(private val view: ThirdPlaceView) {

    var thirdPlace = ThirdPlaceModel()
    var app: MainApp = view.application as MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false

    init {
        if (view.intent.hasExtra("thirdPlace_edit")) {
            edit = true
            thirdPlace = view.intent.extras?.getParcelable("thirdPlace_edit")!!
            view.showThirdPlace(thirdPlace)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String, type: String, amenities: List<String>) {
        thirdPlace.title = title
        thirdPlace.description = description
        thirdPlace.type = type
        thirdPlace.amenities = amenities
        if (validateThirdPlace()) {
            if (edit) {
                app.thirdPlaces.update(thirdPlace)
            } else {
                app.thirdPlaces.create(thirdPlace)
            }
            view.setResult(RESULT_OK)
            view.finish()
        }
    }

    private fun validateThirdPlace(): Boolean {
        when {
            thirdPlace.title.isEmpty() -> {
                view.showError(R.string.error_titleTypeMissing)
                return false
            }
            thirdPlace.title.length > 25 -> {
                view.showError(R.string.error_titleLength)
                return false
            }
            thirdPlace.description.length > 100 -> {
                view.showError(R.string.error_descriptionLength)
                return false
            }
        }
        return true
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        view.setResult(99)
        app.thirdPlaces.delete(thirdPlace)
        view.finish()
    }

    fun doSelectImage() {
        //   showImagePicker(imageIntentLauncher,view)  
        val request = PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
        imageIntentLauncher.launch(request)
    }

    fun doSetLocation() {
        val location = Location(52.245696, -7.139102, 15f)
        if (thirdPlace.zoom != 0f) {
            location.lat =  thirdPlace.lat
            location.lng = thirdPlace.lng
            location.zoom = thirdPlace.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheThirdPlace (title: String, description: String) {
        thirdPlace.title = title
        thirdPlace.description = description
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher = view.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            try{
                view.contentResolver
                    .takePersistableUriPermission(it!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION )
                thirdPlace.image = it // The returned Uri  
                Timber.i("IMG :: ${thirdPlace.image}")
                view.updateImage(thirdPlace.image)
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            thirdPlace.lat = location.lat
                            thirdPlace.lng = location.lng
                            thirdPlace.zoom = location.zoom
                        } // end of if  
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}