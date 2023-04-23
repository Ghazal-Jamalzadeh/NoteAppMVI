package com.jmzd.ghazal.noteappmvi.viewmodel.main

import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity

sealed class MainIntent{
    object LoadAllNotes : MainIntent()
    data class SearchNote(val search : String) : MainIntent()
    data class FilterNote(val filter: String) : MainIntent()
    data class DeleteNote(val entity: NoteEntity) : MainIntent()

}
