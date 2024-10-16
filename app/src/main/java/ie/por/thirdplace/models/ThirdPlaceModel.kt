package ie.por.thirdplace.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThirdPlaceModel(var id: Long = 0, var title: String = "", var description: String = "", var amenities: List<String> = listOf(), var type: String = "", var image: Uri = Uri.EMPTY) : Parcelable
