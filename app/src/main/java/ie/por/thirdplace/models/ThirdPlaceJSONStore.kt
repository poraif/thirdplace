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

    override fun create(thirdPlace: ThirdPlaceModel) {
        thirdPlace.id = generateRandomId()
        thirdPlaces.add(thirdPlace)
        serialize()
    }


    override fun update(thirdPlace: ThirdPlaceModel) {
        // todo
    }

    override fun delete(thirdPlace: ThirdPlaceModel) {
        TODO("Not yet implemented")
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