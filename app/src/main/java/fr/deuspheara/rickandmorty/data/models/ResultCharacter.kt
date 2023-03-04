package fr.deuspheara.rickandmorty.data.models

import com.squareup.moshi.Json

data class ResultCharacter (
    /** Result of the character. */
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "species")
    val species: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "gender")
    val gender: String? = null,
    @Json(name = "origin")
    val origin: UrlModel? = null,
    @Json(name = "location")
    val location: UrlModel? = null,
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "episode")
    val episode: List<String>? = null,
    @Json(name = "url")
    val url: String? = null,
    @Json(name = "created")
    val created: String? = null
)