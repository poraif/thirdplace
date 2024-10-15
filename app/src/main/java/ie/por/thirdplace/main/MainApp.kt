package ie.por.thirdplace.main

import android.app.Application
import ie.por.thirdplace.models.ThirdPlaceMemStore
import ie.por.thirdplace.models.ThirdPlaceModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val thirdPlaces = ThirdPlaceMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Third Place started")
    }
}