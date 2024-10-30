package ie.por.thirdplace.views.signup

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.views.signup.SignupView
import ie.por.thirdplace.models.user.UserStore
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R

class SignupPresenter(val view: SignupView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }


    private lateinit var signupResultLauncher: ActivityResultLauncher<Intent>

    init {
        registerSignupCallback()
    }

    private fun registerSignupCallback() {
        signupResultLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) showSignupSuccess()
                else
                    showSignupError("Signup was unsuccessful.")
            }
    }



    fun doSignup(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            showValidationError("All fields are required.")
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showValidationError("Invalid email address.")
            return
        }
        if (UserStore.findByEmail(email) != null) {
            showSignupError("This email is already registered.")
            return
        }

        val newUser = UserModel(name, email, password)
        UserStore.create(newUser)

        val launcherIntent = Intent(view.getContext()
        signupResultLauncher.launch(launcherIntent)
    }


    fun showSignupSuccess() {
        Snackbar.make(binding.root, R.string.error_titleTypeMissing, Snackbar.LENGTH_LONG).show()
        // show new screen
    }

    fun showSignupError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }

    fun showValidationError(message: String) {
        Snackbar.make(this, "Validation Error: $message", Snackbar.LENGTH_LONG).show()
    }
}