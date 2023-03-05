package fr.deuspheara.rickandmorty.data.api

import fr.deuspheara.rickandmorty.data.models.CharacterRM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {
    /**
     * Get characters from API
     * @param page: Int? = null
     * @return Response<CharacterRM>
     */
    @GET("character")
    suspend fun getCharacterPaging(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("type") gender: String? = null
    ): Response<CharacterRM>


}