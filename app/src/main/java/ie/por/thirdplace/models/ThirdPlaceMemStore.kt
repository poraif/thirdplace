package ie.por.thirdplace.models

import timber.log.Timber.i

class ThirdPlaceMemStore : ThirdPlaceStore {

    val thirdPlaces = ArrayList<ThirdPlaceModel>()

    override fun findAll(): List<ThirdPlaceModel> {
        return thirdPlaces
    }

    override fun create(thirdPlace: ThirdPlaceModel) {
        thirdPlaces.add(thirdPlace)
        logAll()
    }

    override fun update(thirdPlace: ThirdPlaceModel) {
        TODO("Not yet implemented")
    }

    override fun delete(thirdPlace: ThirdPlaceModel) {
        TODO("Not yet implemented")
    }

    fun logAll() {
        thirdPlaces.forEach { i("${it}") }
    }

}