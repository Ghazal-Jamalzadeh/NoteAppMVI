package com.jmzd.ghazal.noteappmvi.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvi.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(){

    private val _state = MutableStateFlow<MainState>(MainState.Empty)
    val state : StateFlow<MainState> get() = _state

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadAllNotes -> fetchingAllNotesList()
            is MainIntent.SearchNote -> fetchingSearchNote(intent.search)
            is MainIntent.FilterNote -> fetchingFilterNote(intent.filter)
            is MainIntent.DeleteNote -> deletingNote(intent.entity)
            is MainIntent.ClickToDetail -> goToDetailPage(intent.id)
        }
    }

    private fun fetchingAllNotesList() = viewModelScope.launch {
        val data = repository.allNotes()
        data.collect {
            //it : MutableList<NoteEntity>
            _state.value = if (it.isNotEmpty()) {
                MainState.LoadNotes(it)
            } else {
                MainState.Empty
            }
        }
    }


    private fun deletingNote(entity: NoteEntity) = viewModelScope.launch {
        _state.value = MainState.DeleteNote(repository.deleteNote(entity))
    }

    private fun fetchingFilterNote(filter: String) = viewModelScope.launch {
        val data = repository.filterNotes(filter)
        data.collect {
            _state.value = if (it.isNotEmpty()) {
                MainState.LoadNotes(it)
            } else {
                MainState.Empty
            }
        }
    }

    private fun fetchingSearchNote(search: String) = viewModelScope.launch {
        val data = repository.searchNotes(search)
        data.collect {
            _state.value = if (it.isNotEmpty()) {
                MainState.LoadNotes(it)
            } else {
                MainState.Empty
            }
        }
    }

    private fun goToDetailPage(id: Int) {
        _state.value = MainState.GoToDetail(id)
    }

}