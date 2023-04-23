package com.jmzd.ghazal.noteappmvi.viewmodel.main

import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity

sealed class MainState{
    data class LoadNotes (val list : MutableList<NoteEntity>) : MainState()
    object Empty : MainState()
}
