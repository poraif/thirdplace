package ie.por.thirdplace.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.por.thirdplace.R
import ie.por.thirdplace.adapters.ThirdPlaceAdapter
import ie.por.thirdplace.adapters.ThirdPlaceListener
import ie.por.thirdplace.databinding.ActivityThirdplaceListBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel

class ThirdPlaceListActivity : AppCompatActivity(), ThirdPlaceListener {

    lateinit var app: MainApp
    private var position: Int = 0
    private lateinit var binding: ActivityThirdplaceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdplaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ThirdPlaceAdapter(app.thirdPlaces.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ThirdplaceMapsActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, ThirdplaceMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val mapIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {  }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.thirdPlaces.findAll().size)
            }
        }

    override fun onThirdPlaceClick(thirdPlace: ThirdPlaceModel, pos: Int) {
        val launcherIntent = Intent(this, ThirdPlaceView::class.java)
        launcherIntent.putExtra("thirdPlace_edit", thirdPlace)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.
            notifyItemRangeChanged(0,app.thirdPlaces.findAll().size)
        }
        else
            if (it.resultCode == 99)
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
    }
}