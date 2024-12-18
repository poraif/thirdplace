package ie.por.thirdplace.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var name: String = "",
                     var email: String = "",
                     var password: String = "",
                     var userId: String = "") : Parcelable
