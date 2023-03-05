package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.rickandmorty.data.models.CharacterRM
import fr.deuspheara.rickandmorty.data.models.ResultCharacter
import fr.deuspheara.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository) : ViewModel() {

    val charactersListLiveData : LiveData<CharacterRM>
        get() = repository.characters

    fun getCharacters(page: Int? = null) {
        viewModelScope.launch {
            repository.getCharacters(page)
        }
    }
}