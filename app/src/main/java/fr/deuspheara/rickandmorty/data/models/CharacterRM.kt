package fr.deuspheara.rickandmorty.data.models

import com.squareup.moshi.Json

data class CharacterRM (
    /** Character model. */
    @Json(name = "info")
    val info: Info,
    @Json(name = "results")
    val results: List<ResultCharacter>
)

