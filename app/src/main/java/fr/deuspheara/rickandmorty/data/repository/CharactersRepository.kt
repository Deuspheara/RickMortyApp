package fr.deuspheara.rickandmorty.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import fr.deuspheara.rickandmorty.RickMortyApp
import fr.deuspheara.rickandmorty.data.api.CharactersApi
import fr.deuspheara.rickandmorty.data.models.CharacterRM
import fr.deuspheara.rickandmorty.data.models.ResultCharacter
import fr.deuspheara.rickandmorty.data.paging.CharactersPagingSource
import fr.deuspheara.rickandmorty.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersApi: CharactersApi,
) {

    private val _characters = MutableLiveData<CharacterRM>()
    val characters: LiveData<CharacterRM>
        get() = _characters

    fun getCharacters() : Flow<PagingData<ResultCharacter>> {
        Log.d("CharactersRepository", "getCharacters")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharactersPagingSource(charactersApi) }
        ).flow
    }

    suspend fun searchCharacters(name: String) : LiveData<CharacterRM> {
        Log.d("CharactersRepository", "searchCharacters: $name")
        val data = MutableLiveData<CharacterRM>()
        if (NetworkUtils.provideIsNetworkAvailable(RickMortyApp.applicationContext())) {
                val response = charactersApi.getCharacterPaging(name = name)
                if (response.isSuccessful) {
                    data.value = response.body()
                }
        }
        return data
    }

}