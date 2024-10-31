package ie.por.thirdplace.views.thirdPlace

import android.os.Bundle
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.databinding.ActivityAddplaceBinding
import ie.por.thirdplace.models.thirdplace.ThirdPlaceModel
import timber.log.Timber.i
import com.squareup.picasso.Picasso
import ie.por.thirdplace.R
import ie.por.thirdplace.helpers.AuthHelper

class ThirdPlaceView : AppCompatActivity() {

    private lateinit var binding: ActivityAddplaceBinding
    private lateinit var presenter: ThirdPlacePresenter
    var thirdPlace = ThirdPlaceModel()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AuthHelper.checkLogin(this)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = ThirdPlacePresenter(this)


        binding.btnAddPlace.setOnClickListener {
            thirdPlace.title = binding.thirdPlaceTitle.text.toString()
            thirdPlace.description = binding.thirdPlaceDescription.text.toString()
            thirdPlace.type = getSelectedPlaceType()
            thirdPlace.amenities = getSelectedAmenities()

            presenter.doAddOrSave(
                thirdPlace.title,
                thirdPlace.description,
                thirdPlace.type,
                thirdPlace.amenities
            )
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

    private fun getSelectedPlaceType(): String {
        val selectedTypeId = binding.placeTypeRadioGroup.checkedRadioButtonId
        val selectedTypeRadioButton = findViewById<RadioButton>(selectedTypeId)
        return selectedTypeRadioButton.text.toString()
    }

    private fun getSelectedAmenities(): List<String> {
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
        return selectedAmenities
    }

    fun showError(messageResId: Int) {
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_LONG).show()
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