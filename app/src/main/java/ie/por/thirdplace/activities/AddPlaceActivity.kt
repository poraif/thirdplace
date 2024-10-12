package ie.por.thirdplace.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.databinding.ActivityAddplaceBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel
import timber.log.Timber
import timber.log.Timber.i

class AddPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddplaceBinding
    var thirdPlace = ThirdPlaceModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Placemark Activity started...")

        binding.btnAdd.setOnClickListener() {
            thirdPlace.title = binding.thirdPlaceTitle.text.toString()
            thirdPlace.description = binding.thirdPlaceDescription.text.toString()
            thirdPlace.type = binding.placeTypeRadioGroup.checkedRadioButtonId.toString()
            if (thirdPlace.title.isNotEmpty() && thirdPlace.type.isNotEmpty()) {
                i("add Button Pressed: $thirdPlace.title")
                app.thirdPlaces.add(thirdPlace.copy())
                for (i in app.thirdPlaces.indices)
                { i("Placemark[$i]:${this.app.thirdPlaces[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}