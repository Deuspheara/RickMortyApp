package fr.deuspheara.rickandmorty.presentation.characters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.rickandmorty.data.models.CharacterRM
import fr.deuspheara.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository) : ViewModel() {

    val charactersListLiveData : LiveData<List<CharacterRM>>
        get() = repository.characters

    init {
        viewModelScope.launch {
            repository.getCharacters()
        }
    }
}