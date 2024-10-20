package ie.por.thirdplace.models

interface ThirdPlaceStore {
    fun findAll(): List<ThirdPlaceModel>
    fun findById(id: Long): ThirdPlaceModel?
    fun create(thirdPlace: ThirdPlaceModel)
    fun update(thirdPlace: ThirdPlaceModel)
    fun delete(thirdPlace: ThirdPlaceModel)
}