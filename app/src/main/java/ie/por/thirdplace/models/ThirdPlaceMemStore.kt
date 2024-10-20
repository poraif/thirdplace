package ie.por.thirdplace.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ThirdPlaceMemStore : ThirdPlaceStore {

    val thirdPlaces = ArrayList<ThirdPlaceModel>()

    override fun findAll(): List<ThirdPlaceModel> {
        return thirdPlaces
    }

    override fun findById(id: Long): ThirdPlaceModel? {
        val foundThirdPlace: ThirdPlaceModel? = thirdPlaces.find { it.id == id }
        return foundThirdPlace
    }

    override fun create(thirdPlace: ThirdPlaceModel) {
        thirdPlace.id = getId()
        thirdPlaces.add(thirdPlace)
        logAll()
    }

    override fun update(thirdPlace: ThirdPlaceModel) {
        var thirdPlaceToUpdate: ThirdPlaceModel? = thirdPlaces.find { p -> p.id == thirdPlace.id }
        if (thirdPlaceToUpdate != null) {
            thirdPlaceToUpdate.title = thirdPlace.title
            thirdPlaceToUpdate.description = thirdPlace.description
            thirdPlaceToUpdate.amenities = thirdPlace.amenities
            thirdPlaceToUpdate.type = thirdPlace.type
            thirdPlaceToUpdate.image = thirdPlace.image
            thirdPlaceToUpdate.lat = thirdPlace.lat
            thirdPlaceToUpdate.lng = thirdPlace.lng
            thirdPlaceToUpdate.zoom = thirdPlace.zoom
            logAll()
        }
    }

        override fun delete(thirdPlace: ThirdPlaceModel) {
            thirdPlaces.remove(thirdPlace)
        }

        private fun logAll() {
            thirdPlaces.forEach { i("$it") }
        }
    }
