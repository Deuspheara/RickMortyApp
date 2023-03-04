package fr.deuspheara.rickandmorty.data.models

import com.squareup.moshi.Json

data class Info(
    /** Infos on pagination. */
    @Json(name = "count")
    val count: Int = 0,
    @Json(name = "pages")
    val pages: Int? = null,
    @Json(name = "next")
    val next: String? = null,
    @Json(name = "prev")
    val prev: String? = null,
)