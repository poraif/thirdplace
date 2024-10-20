package ie.por.thirdplace.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.por.thirdplace.R
import ie.por.thirdplace.adapters.ThirdPlaceAdapter
import ie.por.thirdplace.adapters.ThirdPlaceListener
import ie.por.thirdplace.databinding.ActivityThirdplaceListBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel

class ThirdPlaceListView : AppCompatActivity(), ThirdPlaceListener {

    lateinit var app: MainApp
    private var position: Int = 0
    lateinit var presenter: ThirdPlaceListPresenter
    private lateinit var binding: ActivityThirdplaceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityThirdplaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = ThirdPlaceListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadThirdPlaces()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddThirdPlace() }
            R.id.item_map -> { presenter.doShowThirdPlacesMap() }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onThirdPlaceClick(thirdPlace: ThirdPlaceModel, pos: Int) {
        this.position = pos
        presenter.doEditThirdPlace(thirdPlace, this.position)
    }

    private fun loadThirdPlaces() {
        binding.recyclerView.adapter = ThirdPlaceAdapter(presenter.getThirdPlaces(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getThirdPlaces().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }
}