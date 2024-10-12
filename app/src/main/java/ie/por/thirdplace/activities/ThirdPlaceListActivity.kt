package ie.por.thirdplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.por.thirdplace.databinding.ActivityThirdplaceListBinding
import ie.por.thirdplace.databinding.CardThirdplaceBinding
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.ThirdPlaceModel

class ThirdPlaceListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityThirdplaceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdplaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ThirdPlaceAdapter(app.thirdPlaces)
    }
}

class ThirdPlaceAdapter constructor(private var thirdPlaces: List<ThirdPlaceModel>) :
    RecyclerView.Adapter<ThirdPlaceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardThirdplaceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val thirdPlace = thirdPlaces[holder.adapterPosition]
        holder.bind(thirdPlace)
    }

    override fun getItemCount(): Int = thirdPlaces.size

    class MainHolder(private val binding : CardThirdplaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(thirdPlace: ThirdPlaceModel) {
            binding.thirdPlaceTitle.text = thirdPlace.title
            binding.thirdPlaceType.text = thirdPlace.type
        }
    }
}