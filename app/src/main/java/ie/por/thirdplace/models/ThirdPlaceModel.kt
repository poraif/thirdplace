package ie.por.thirdplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThirdPlaceModel(var title: String = "", var description: String = "", var amenities: List<String> = listOf(), var type: String = "") : Parcelable
