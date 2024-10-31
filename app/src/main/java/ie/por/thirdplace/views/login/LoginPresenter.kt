package ie.por.thirdplace.views.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.R
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserJSONStore
import ie.por.thirdplace.views.thirdPlace.ThirdPlaceView
import ie.por.thirdplace.views.thirdPlaceList.ThirdPlaceListView


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
        val sharedPreferences = view.applicationContext.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", true).apply()

        Snackbar.make(view.binding.root, R.string.success_signup, Snackbar.LENGTH_LONG).show()
        view.finish()
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
                if (user != null) {
                    app.loggedInUser = user
                    val launcherIntent = Intent(view, ThirdPlaceListView::class.java)
                    loginResultLauncher.launch(launcherIntent)
                }
            }
        }
    }

    fun doLogout() {
        val sharedPreferences = view.applicationContext.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()

        app.loggedInUser = null

        val logoutIntent = Intent(view, LoginView::class.java)
        logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear activity stack
        view.startActivity(logoutIntent)
        view.finish()
    }
}