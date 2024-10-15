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
import ie.por.thirdplace.R

class AddPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddplaceBinding
    var thirdPlace = ThirdPlaceModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Third Place Activity started...")

        binding.btnAddPlace.setOnClickListener() {
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

        if (thirdPlace.title.isNotEmpty() && thirdPlace.type.isNotEmpty()) {
                i("add Button Pressed: $thirdPlace.title")
                app.thirdPlaces.create(thirdPlace.copy())
                setResult(RESULT_OK)
                finish()
        }
        else {
            Snackbar.make(it,"Please Enter a title and select a type", Snackbar.LENGTH_LONG)
                .show()
        }
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