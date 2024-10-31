package ie.por.thirdplace.main

import android.app.Application
import android.content.Intent
import ie.por.thirdplace.models.thirdplace.ThirdPlaceJSONStore
import ie.por.thirdplace.models.thirdplace.ThirdPlaceStore
import ie.por.thirdplace.models.user.UserJSONStore
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.models.user.UserStore
import ie.por.thirdplace.views.login.LoginView
import ie.por.thirdplace.views.thirdPlaceList.ThirdPlaceListView
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var thirdPlaces: ThirdPlaceStore
    lateinit var user: UserStore
    var loggedInUser: UserModel? = null

    fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        if (isLoggedIn()) {
            val loggedInIntent = Intent(this, ThirdPlaceListView::class.java)
            loggedInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(loggedInIntent)
        } else {
            val logInIntent = Intent(this, LoginView::class.java)
            logInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logInIntent)
        }

        thirdPlaces = ThirdPlaceJSONStore(applicationContext)
        user = UserJSONStore(applicationContext)
        i("Third Place started")

    }
}