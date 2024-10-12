package ie.por.thirdplace.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.databinding.ActivityAddplaceBinding
import timber.log.Timber
import timber.log.Timber.i

class AddPlaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddplaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener() {
            val thirdPlaceTitle = binding.thirdPlaceTitle.text.toString()
            val thirdPlaceDescription = binding.thirdPlaceDescription.text.toString()
            val thirdPlaceType = binding.placeTypeRadioGroup.checkedRadioButtonId
            if (thirdPlaceTitle.isNotEmpty()) {
                i("add Button Pressed: $thirdPlaceTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        Timber.plant(Timber.DebugTree())
        i("Third Place Add Place activity started...")
    }
}