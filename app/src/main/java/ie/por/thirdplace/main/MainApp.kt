package ie.por.thirdplace.main

import android.app.Application
import ie.por.thirdplace.models.thirdplace.ThirdPlaceJSONStore
import ie.por.thirdplace.models.thirdplace.ThirdPlaceStore
import ie.por.thirdplace.models.user.UserJSONStore
import ie.por.thirdplace.models.user.UserModel
import ie.por.thirdplace.models.user.UserStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var thirdPlaces: ThirdPlaceStore
    lateinit var user: UserStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        thirdPlaces = ThirdPlaceJSONStore(applicationContext)
        user = UserJSONStore(applicationContext)
        i("Third Place started")
    }
}