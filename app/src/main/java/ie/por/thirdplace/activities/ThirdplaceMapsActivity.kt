package ie.por.thirdplace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import ie.por.thirdplace.databinding.ActivityThirdplaceMapsBinding

class ThirdplaceMapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdplaceMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThirdplaceMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }
}