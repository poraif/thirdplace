package ie.por.thirdplace.activities

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel
import ie.por.thirdplace.models.Location
import ie.por.thirdplace.views.editlocation.EditLocationView
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
            //thirdPlace = view.intent.getParcelableExtra("thirdPlace_edit",ThirdPlaceModel::class.java)!!  
            thirdPlace = view.intent.extras?.getParcelable("thirdPlace_edit")!!
            view.showThirdPlace(thirdPlace)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String) {
        thirdPlace.title = title
        thirdPlace.description = description
        if (edit) {
            app.thirdPlaces.update(thirdPlace)
        } else {
            app.thirdPlaces.create(thirdPlace)
        }
        view.setResult(RESULT_OK)
        view.finish()
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
                            //val location = result.data!!.extras?.getParcelable("location",Location::class.java)!!  
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