package ie.por.thirdplace.views.login

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.views.login.LoginView
import ie.por.thirdplace.models.user.UserStore
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.models.user.UserJSONStore
import ie.por.thirdplace.views.thirdPlaceMap.ThirdPlaceMapView

class LoginPresenter(val view: LoginView) {

    var app: MainApp
    private val userStore: UserJSONStore

    init {
        app = view.application as MainApp
        userStore = UserJSONStore(view.applicationContext)
        registerLoginCallback()
    }


    private lateinit var loginResultLauncher: ActivityResultLauncher<Intent>


    private fun registerLoginCallback() {
        loginResultLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) showLoginSuccess()
                else
                    view.showError(R.string.error_login)
            }
    }


    private fun showLoginSuccess() {
        Snackbar.make(view.binding.root, R.string.success_signup, Snackbar.LENGTH_LONG).show()
    }


    fun doLogin(email: String, password: String) {
        when {
            email.isBlank() || password.isBlank() -> {
                view.showError(R.string.error_emptyFields)
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.showError(R.string.error_email)
                return
            }
            userStore.findByEmail(email) == null -> {
                view.showError(R.string.error_emailNotFound)
                return
            }
            userStore.findByEmail(email)?.password != password -> {
                view.showError(R.string.error_password)
                return
            }
            else -> {
                val user = userStore.findByEmail(email)
                if (user != null) { // Check if user is not null before accessing
                    app.loggedInUser = user
                    val launcherIntent = Intent(view, ThirdPlaceMapView::class.java)
                    loginResultLauncher.launch(launcherIntent)
                }
            }
        }
    }
}