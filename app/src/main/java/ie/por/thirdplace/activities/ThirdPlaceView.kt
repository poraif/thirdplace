package ie.por.thirdplace.activities

import android.os.Bundle
import android.content.Intent
import android.net.Uri
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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.squareup.picasso.Picasso
import ie.por.thirdplace.R
import ie.por.thirdplace.models.Location

@Suppress("DEPRECATION")
class ThirdPlaceView : AppCompatActivity() {

    private lateinit var binding: ActivityAddplaceBinding
    private lateinit var presenter: ThirdPlacePresenter
    var thirdPlace = ThirdPlaceModel()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = ThirdPlacePresenter(this)


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
            presenter.cacheThirdPlace(binding.thirdPlaceTitle.text.toString(), binding.thirdPlaceDescription.text.toString())
            presenter.doSelectImage()
        }

        binding.thirdPlaceLocation.setOnClickListener {
            presenter.cacheThirdPlace(binding.thirdPlaceTitle.text.toString(), binding.thirdPlaceDescription.text.toString())
            presenter.doSetLocation()
        }

        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_addplace, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> { presenter.doCancel()  }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showThirdPlace(thirdPlace: ThirdPlaceModel) {
        binding.thirdPlaceTitle.setText(thirdPlace.title)
        binding.thirdPlaceDescription.setText(thirdPlace.description)
        binding.btnAddPlace.setText(R.string.button_addPlace)
        Picasso.get()
            .load(thirdPlace.image)
            .into(binding.thirdPlaceImage)
        if (thirdPlace.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.button_updateImage)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.thirdPlaceImage)
        binding.chooseImage.setText(R.string.button_updateImage)
    }

}