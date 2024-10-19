package ie.por.thirdplace.models

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

fun generateRandomId(): Long {
    return Random().nextLong()
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

    override fun findById(id: Long): ThirdPlaceModel? {
        val foundThirdPlace: ThirdPlaceModel? = thirdPlaces.find { it.id == id }
        return foundThirdPlace
    }

    override fun create(thirdPlace: ThirdPlaceModel) {
        thirdPlace.id = generateRandomId()
        thirdPlaces.add(thirdPlace)
        serialize()
    }


    override fun update(thirdPlace: ThirdPlaceModel) {
        val thirdplacesList = findAll() as ArrayList<ThirdPlaceModel>
        var ThirdPlaceToUpdate: ThirdPlaceModel? = thirdplacesList.find { p -> p.id == thirdPlace.id }
        if (ThirdPlaceToUpdate != null) {
            ThirdPlaceToUpdate.title = thirdPlace.title
            ThirdPlaceToUpdate.description = thirdPlace.description
            ThirdPlaceToUpdate.image = thirdPlace.image
            ThirdPlaceToUpdate.type = thirdPlace.type
            ThirdPlaceToUpdate.amenities = thirdPlace.amenities
            ThirdPlaceToUpdate.lat = thirdPlace.lat
            ThirdPlaceToUpdate.lng = thirdPlace.lng
            ThirdPlaceToUpdate.zoom = thirdPlace.zoom
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