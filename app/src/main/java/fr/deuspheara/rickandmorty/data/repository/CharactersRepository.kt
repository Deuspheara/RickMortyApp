package fr.deuspheara.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.deuspheara.rickandmorty.RickMortyApp
import fr.deuspheara.rickandmorty.data.api.CharactersApi
import fr.deuspheara.rickandmorty.data.models.CharacterRM
import fr.deuspheara.rickandmorty.utils.NetworkUtils
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersApi: CharactersApi,
) {
    private val _characters = MutableLiveData<CharacterRM>()
    val characters : LiveData<CharacterRM>
        get() = _characters


    /**
     * Get characters from API
     * @param page: Int? = null
     * @return Response<CharacterRM>
     */
    suspend fun getCharacters(page: Int? = null) {
        if(NetworkUtils.provideIsNetworkAvailable(RickMortyApp.applicationContext())) {
            val response = charactersApi.getCharacterPaging(page)
            if (response.isSuccessful && response.body() != null) {
                _characters.postValue(response.body())
            }
        }
    }

}