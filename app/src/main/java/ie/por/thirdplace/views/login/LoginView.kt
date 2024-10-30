package ie.por.thirdplace.views.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserJSONStore
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.databinding.ActivityLoginBinding
import ie.por.thirdplace.views.signup.SignupPresenter
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.views.login.LoginPresenter
import ie.por.thirdplace.views.signup.SignupView

class LoginView : AppCompatActivity() {

    lateinit var app: MainApp
    lateinit var binding: ActivityLoginBinding
    var user = UserModel()
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = LoginPresenter(this)

        binding.loginButton.setOnClickListener {
            user.email = binding.emailField.text.toString()
            user.password = binding.passwordField.text.toString()
            presenter.doLogin(
                user.email,
                user.password
            )
        }
    }

    fun showError(messageResId: Int) {
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_welcome, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_signup -> {
                val signupIntent = Intent(this, SignupView::class.java)
                startActivity(signupIntent)
                true
            }

            R.id.item_login -> {
                val loginIntent = Intent(this, LoginView::class.java)
                startActivity(loginIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}