package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.rickandmorty.data.models.CharacterRM
import fr.deuspheara.rickandmorty.data.models.ResultCharacter
import fr.deuspheara.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository) : ViewModel() {


    fun getCharacters() : Flow<PagingData<ResultCharacter>> {
        return repository.getCharacters().cachedIn(viewModelScope)
    }

    suspend fun searchCharacters(name: String) : LiveData<CharacterRM> {
        return repository.searchCharacters(name)
    }
}