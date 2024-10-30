package ie.por.thirdplace.views.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.thirdplace.ThirdPlaceModel
import ie.por.thirdplace.models.thirdplace.Location
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.views.editLocation.EditLocationView
import timber.log.Timber
import android.util.Patterns
import ie.por.thirdplace.views.login.LoginView

class LoginPresenter(private val view: LoginView) {

    var user = UserModel()
    var app: MainApp = view.application as MainApp


    fun login(email: String, password: String) {
        user.email = email
        user.password = password
        if (validateUser()) {
            view.setResult(RESULT_OK)
            view.finish()
        }
    }

    fun validateUser(): Boolean {
        when {
            user.email.isEmpty() -> {
                view.showError(R.string.error_titleTypeMissing)
                return false
            }
            user.email.length > 25 -> {
                view.showError(R.string.error_titleLength)
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(user.email).matches() -> {
                view.showError(R.string.error_emailInvalid)
                return false
            }
            user.password.length > 25 -> {
                view.showError(R.string.error_titleLength)
                return false
            }
            user.email != app.user.email -> {
                view.showError(R.string.error_emailNotFound)
                return false
            }
            user.password != app.user.password -> {
                view.showError(R.string.error_passwordIncorrect)
            }

        }
        return true
}
    }