package ie.por.thirdplace.models.user

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.por.thirdplace.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "users.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): String {
    return UUID.randomUUID().toString()

}

class UserJSONStore(private val context: Context) : UserStore {

    var users = mutableListOf<UserModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        logAll()
        return users
    }

    override fun findById(userId: String): UserModel? {
        val foundUser: UserModel? = users.find { it.userId == userId }
        return foundUser
    }

    override fun findByEmail(email: String): UserModel? {
        val foundUser: UserModel? = users.find { it.email == email }
        return foundUser
    }

    override fun create(user: UserModel) {
        user.userId = generateRandomId()
        users.add(user)
        serialize()
    }


    override fun update(user: UserModel) {
        val usersList = findAll() as ArrayList<UserModel>
        var userToUpdate: UserModel? =
            usersList.find { p -> p.userId == user.userId }
        if (userToUpdate != null) {
            userToUpdate.name = user.name
            userToUpdate.email = user.email
            userToUpdate.password = user.password
            userToUpdate.userId = user.userId
        }
        serialize()
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
        Timber.i("Third Place Deleted: $user")
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        users.forEach { Timber.i("$it") }
    }

}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

