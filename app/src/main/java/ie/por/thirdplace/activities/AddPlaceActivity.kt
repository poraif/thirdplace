package ie.por.thirdplace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import timber.log.Timber
import timber.log.Timber.i

class AddPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addplace)

        Timber.plant(Timber.DebugTree())
        i("Third Place Activity started...")
    }
}