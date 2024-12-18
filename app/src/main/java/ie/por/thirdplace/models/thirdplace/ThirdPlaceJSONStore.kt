package ie.por.thirdplace.models.thirdplace

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.por.thirdplace.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "thirdplaces.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<ThirdPlaceModel>>() {}.type

fun generateRandomId(): String {
    return UUID.randomUUID().toString()
}

class ThirdPlaceJSONStore(private val context: Context) : ThirdPlaceStore {

    var thirdPlaces = mutableListOf<ThirdPlaceModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ThirdPlaceModel> {
        logAll()
        return thirdPlaces
    }

    override fun findById(id: String): ThirdPlaceModel? {
        val foundThirdPlace: ThirdPlaceModel? = thirdPlaces.find { it.id == id }
        return foundThirdPlace
    }

    override fun findByUserId(userId: String): List<ThirdPlaceModel> {
        TODO("Not yet implemented")
    }

    override fun create(thirdPlace: ThirdPlaceModel) {
        thirdPlace.id = generateRandomId()
        thirdPlaces.add(thirdPlace)
        serialize()
    }


    override fun update(thirdPlace: ThirdPlaceModel) {
        val thirdplacesList = findAll() as ArrayList<ThirdPlaceModel>
        var thirdPlaceToUpdate: ThirdPlaceModel? =
            thirdplacesList.find { p -> p.id == thirdPlace.id }
        if (thirdPlaceToUpdate != null) {
            thirdPlaceToUpdate.title = thirdPlace.title
            thirdPlaceToUpdate.description = thirdPlace.description
            thirdPlaceToUpdate.image = thirdPlace.image
            thirdPlaceToUpdate.type = thirdPlace.type
            thirdPlaceToUpdate.amenities = thirdPlace.amenities
            thirdPlaceToUpdate.lat = thirdPlace.lat
            thirdPlaceToUpdate.lng = thirdPlace.lng
            thirdPlaceToUpdate.zoom = thirdPlace.zoom
            thirdPlaceToUpdate.userId = thirdPlace.userId
        }
        serialize()
    }

    override fun delete(thirdPlace: ThirdPlaceModel) {
        thirdPlaces.remove(thirdPlace)
        serialize()
        Timber.i("Third Place Deleted: $thirdPlace")
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(thirdPlaces, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        thirdPlaces = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        thirdPlaces.forEach { Timber.i("$it") }
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

