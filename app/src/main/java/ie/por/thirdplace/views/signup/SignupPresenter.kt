package ie.por.thirdplace.views.signup

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserModel
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.models.user.UserJSONStore
import ie.por.thirdplace.views.login.LoginView

class SignupPresenter(val view: SignupView) {

    var app: MainApp
    private val userStore: UserJSONStore

    init {
        app = view.application as MainApp
        userStore = UserJSONStore(view.applicationContext)
        registerSignupCallback()
    }


    private lateinit var signupResultLauncher: ActivityResultLauncher<Intent>


    private fun registerSignupCallback() {
        signupResultLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) showSignupSuccess()
                else
                    view.showError(R.string.error_signup)
            }
    }



    fun doSignup(name: String, email: String, password: String) {
        when {
            name.isBlank() || email.isBlank() || password.isBlank() -> {
                view.showError(R.string.error_emptyFields)
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.showError(R.string.error_email)
                return
            }
            userStore.findByEmail(email) != null -> {
                view.showError(R.string.error_emailUsed)
                return
            }
            else -> {
                val newUser = UserModel(name, email, password)
                userStore.create(newUser)
                val launcherIntent = Intent(view, LoginView::class.java)
                signupResultLauncher.launch(launcherIntent)
            }
        }
    }


    private fun showSignupSuccess() {
        Snackbar.make(view.binding.root, R.string.success_signup, Snackbar.LENGTH_LONG).show()
        view.finish()
    }

}