package com.jmzd.ghazal.noteappmvi.viewmodel.note

import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity

sealed class DetailIntent {
    object SpinnersList : DetailIntent()
    data class SaveNote(val entity: NoteEntity) : DetailIntent()
}


