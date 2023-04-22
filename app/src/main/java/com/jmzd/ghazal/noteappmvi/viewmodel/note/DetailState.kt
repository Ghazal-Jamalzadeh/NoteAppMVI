package com.jmzd.ghazal.noteappmvi.viewmodel.note

sealed class DetailState {
    object Idle : DetailState()
data class SpinnersData (val categories : MutableList<String> , val priorities: MutableList<String>) : DetailState()
    data class Error(val message : String) :DetailState()
//    object SaveNote : DetailState()
    data class SaveNote(val unit : Unit) : DetailState()
}
