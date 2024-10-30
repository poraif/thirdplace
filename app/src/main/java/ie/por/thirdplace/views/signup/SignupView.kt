package ie.por.thirdplace.views.signup

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.R
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.models.user.UserJSONStore
import com.google.android.material.snackbar.Snackbar
import ie.por.thirdplace.views.signup.SignupPresenter
import ie.por.thirdplace.views.signup.SignupView
import ie.por.thirdplace.databinding.ActivitySignupBinding
import ie.por.thirdplace.models.user.UserModel

class SignupView : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySignupBinding
    var user = UserModel()
    lateinit var presenter: SignupPresenter
    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        nameField = findViewById(R.id.nameField)
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)

        presenter = SignupPresenter(this)

        binding.signupButton.setOnClickListener {
            user.name = binding.nameField.text.toString()
            user.email = binding.emailField.text.toString()
            user.password = binding.passwordField.text.toString()
            presenter.doSignup(
                user.name,
                user.email,
                user.password
            )
        }
    }
}