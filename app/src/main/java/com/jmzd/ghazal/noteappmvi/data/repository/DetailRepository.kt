package com.jmzd.ghazal.noteappmvi.data.repository

import com.jmzd.ghazal.noteappmvi.data.database.NoteDao
import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepository @Inject  constructor(private val dao : NoteDao){
   suspend fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
   suspend fun updateNote(entity: NoteEntity) = dao.updateNote(entity)
   fun getNote(id : Int ) : Flow<NoteEntity> = dao.getNote(id = id )
}