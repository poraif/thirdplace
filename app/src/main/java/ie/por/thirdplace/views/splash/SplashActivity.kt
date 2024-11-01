package ie.por.thirdplace.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.views.login.LoginView
import ie.por.thirdplace.views.thirdPlaceList.ThirdPlaceListView

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val app = application as MainApp
            val nextActivity = if (app.isLoggedIn()) {
                ThirdPlaceListView::class.java
            } else {
                LoginView::class.java
            }
            val intent = Intent(this, nextActivity)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }
}