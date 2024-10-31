package ie.por.thirdplace.helpers

import android.content.Context
import android.content.Intent
import ie.por.thirdplace.main.MainApp
import ie.por.thirdplace.views.login.LoginView

object AuthHelper {
    fun checkLogin(context: Context) {
        val app = context.applicationContext as MainApp
        if (!app.isLoggedIn()) {
            val loginIntent = Intent(context, LoginView::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(loginIntent)
        }
    }
}
