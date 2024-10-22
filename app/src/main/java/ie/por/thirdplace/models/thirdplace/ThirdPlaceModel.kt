package ie.por.thirdplace.models.thirdplace

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThirdPlaceModel(var id: String = "abc",
                           var title: String = "",
                           var description: String = "",
                           var amenities: List<String> = listOf(),
                           var type: String = "",
                           var image: Uri = Uri.EMPTY,
                           var lat : Double = 0.0,
                           var lng: Double = 0.0,
                           var zoom: Float = 0f,
                           var userId: String = "") : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable