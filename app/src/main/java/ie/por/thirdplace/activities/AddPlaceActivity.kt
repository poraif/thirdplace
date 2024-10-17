package ie.por.thirdplace.activities

import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.databinding.ActivityAddplaceBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel
import timber.log.Timber.i
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import ie.por.thirdplace.R

@Suppress("DEPRECATION")
class AddPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddplaceBinding
    var thirdPlace = ThirdPlaceModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            thirdPlace.image = result.data!!.data!!
                            Picasso.get()
                                .load(thirdPlace.image)
                                .into(binding.thirdPlaceImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var edit = false

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Third Place Activity started...")

        if (intent.hasExtra("thirdPlace_edit")) {
            edit = true
            thirdPlace = intent.extras?.getParcelable("thirdPlace_edit")!!
            binding.thirdPlaceTitle.setText(thirdPlace.title)
            binding.thirdPlaceDescription.setText(thirdPlace.description)
            binding.btnAddPlace.setText(R.string.button_update)
            Picasso.get()
                .load(thirdPlace.image)
                .into(binding.thirdPlaceImage)
        }



        binding.btnAddPlace.setOnClickListener {
            thirdPlace.title = binding.thirdPlaceTitle.text.toString()
            thirdPlace.description = binding.thirdPlaceDescription.text.toString()

            val selectedTypeId = binding.placeTypeRadioGroup.checkedRadioButtonId
            val selectedTypeRadioButton = findViewById<RadioButton>(selectedTypeId)
            thirdPlace.type = selectedTypeRadioButton.text.toString()

            val selectedAmenities = mutableListOf<String>()
            if (binding.checkboxFree.isChecked) selectedAmenities.add(getString(R.string.amenity_free))
            if (binding.checkboxCharging.isChecked) selectedAmenities.add(getString(R.string.amenity_charging))
            if (binding.checkboxToilets.isChecked) selectedAmenities.add(getString(R.string.amenity_toilets))
            if (binding.checkboxShelter.isChecked) selectedAmenities.add(getString(R.string.amenity_shelter))
            if (binding.checkboxQuiet.isChecked) selectedAmenities.add(getString(R.string.amenity_quiet))
            if (binding.checkboxMembership.isChecked) selectedAmenities.add(getString(R.string.amenity_membership))
            if (binding.checkboxLaptop.isChecked) selectedAmenities.add(getString(R.string.amenity_laptop))
            if (binding.checkboxEquipment.isChecked) selectedAmenities.add(getString(R.string.amenity_equipment))
            if (binding.checkboxAlwaysOpen.isChecked) selectedAmenities.add(getString(R.string.amenity_alwaysOpen))
            thirdPlace.amenities = selectedAmenities

        if (thirdPlace.title.isEmpty() || thirdPlace.type.isEmpty()) {
            Snackbar.make(it, R.string.error_titleTypeMissing, Snackbar.LENGTH_LONG)
                .show()
        }
        if (thirdPlace.title.length > 25) {
            Snackbar.make(it, R.string.error_titleLength, Snackbar.LENGTH_LONG)
                .show()
        }
        if (thirdPlace.description.length > 100) {
            Snackbar.make(it, R.string.error_descriptionLength, Snackbar.LENGTH_LONG)
                .show()
        }
        else {
            if (edit) {
                app.thirdPlaces.update(thirdPlace.copy())
            } else {
                app.thirdPlaces.create(thirdPlace.copy())
            }
            setResult(RESULT_OK)
            finish()
        }
        }

        binding.chooseImage.setOnClickListener {
            i("Select image")
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_addplace, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}