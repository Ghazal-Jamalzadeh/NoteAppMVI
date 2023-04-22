package com.jmzd.ghazal.noteappmvi.viewmodel.note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvi.data.repository.DetailRepository
import com.jmzd.ghazal.noteappmvi.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel(){

    val detailIntent = Channel<DetailIntent>()
    private val _state = MutableStateFlow<DetailState>(DetailState.Idle)
    val state: StateFlow<DetailState> get() = _state

    init {
        handleIntents()
    }

    private fun handleIntents() = viewModelScope.launch {
        detailIntent.consumeAsFlow().collect { intent : DetailIntent ->
            when (intent) {
                is DetailIntent.SpinnersList -> fetchingSpinnersList()
                is DetailIntent.SaveNote -> savingData(intent.entity)
//                is DetailIntent.NoteDetail -> fetchingNoteDetail(intent.id)
//                is DetailIntent.UpdateNote -> updatingData(intent.entity)
            }
        }
    }

    private fun fetchingSpinnersList() {
        val categoriesList = mutableListOf(WORK, EDUCATION, HOME, HEALTH)
        val prioritiesList = mutableListOf(HIGH, NORMAL, LOW)
        _state.value = DetailState.SpinnersData(categoriesList, prioritiesList)
//        _state.emit(DetailState.SpinnersData(categoriesList, prioritiesList))
    }

    private fun savingData(entity: NoteEntity) = viewModelScope.launch {

        _state.value = try {
//            DetailState.SaveNote
            DetailState.SaveNote(repository.saveNote(entity))
        } catch (e: Exception) {
            DetailState.Error(e.message.toString())
        }
        Log.d(TAG, "savingData: done")
    }

}