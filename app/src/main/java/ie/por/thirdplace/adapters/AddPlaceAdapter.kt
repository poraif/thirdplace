package ie.por.thirdplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.por.thirdplace.databinding.CardThirdplaceBinding
import ie.por.thirdplace.models.thirdplace.ThirdPlaceModel

interface ThirdPlaceListener {
    fun onThirdPlaceClick(thirdPlace: ThirdPlaceModel, position: Int)
}

class ThirdPlaceAdapter constructor(private var thirdPlaces: List<ThirdPlaceModel>,
                                    private val listener: ThirdPlaceListener) :
    RecyclerView.Adapter<ThirdPlaceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardThirdplaceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val thirdPlace = thirdPlaces[holder.adapterPosition]
        holder.bind(thirdPlace, listener)
    }

    override fun getItemCount(): Int = thirdPlaces.size

    class MainHolder(private val binding : CardThirdplaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(thirdPlace: ThirdPlaceModel, listener: ThirdPlaceListener) {
            binding.thirdPlaceTitle.text = thirdPlace.title
            binding.thirdPlaceType.text = thirdPlace.type
            Picasso.get().load(thirdPlace.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onThirdPlaceClick(thirdPlace, adapterPosition) }
        }
    }
}