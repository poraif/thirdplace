package ie.por.thirdplace.main

import android.app.Application
import ie.por.thirdplace.models.ThirdPlaceJSONStore
import ie.por.thirdplace.models.ThirdPlaceStore
import ie.por.thirdplace.models.ThirdPlaceModel
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