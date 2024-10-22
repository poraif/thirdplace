package ie.por.thirdplace.models.user


interface UserStore {
    fun findAll(): List<UserModel>
    fun findById(userId: String): UserModel?
    fun findByEmail(email: String): UserModel?
    fun create(user: UserModel)
    fun update(user: UserModel)
    fun delete(user: UserModel)
}