package ie.por.thirdplace.models.thirdplace

interface ThirdPlaceStore {
    fun findAll(): List<ThirdPlaceModel>
    fun findById(id: String): ThirdPlaceModel?
    fun findByUserId(userId: String): List<ThirdPlaceModel>
    fun create(thirdPlace: ThirdPlaceModel)
    fun update(thirdPlace: ThirdPlaceModel)
    fun delete(thirdPlace: ThirdPlaceModel)
}