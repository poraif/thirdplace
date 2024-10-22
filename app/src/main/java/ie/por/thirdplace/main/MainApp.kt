package ie.por.thirdplace.main

import android.app.Application
import ie.por.thirdplace.models.thirdplace.ThirdPlaceJSONStore
import ie.por.thirdplace.models.thirdplace.ThirdPlaceStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var thirdPlaces: ThirdPlaceStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        thirdPlaces = ThirdPlaceJSONStore(applicationContext)
        i("Third Place started")
    }
}