package fr.deuspheara.rickandmorty.data.models

import com.squareup.moshi.Json

data class UrlModel(
    /** Infos of url */
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "url")
    val url: String? = null
)