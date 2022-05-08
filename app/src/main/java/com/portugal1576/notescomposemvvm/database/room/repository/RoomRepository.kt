package com.portugal1576.notescomposemvvm.database.room.dao.repository

import androidx.lifecycle.LiveData
import com.portugal1576.notescomposemvvm.database.DatabaseRepository
import com.portugal1576.notescomposemvvm.database.room.dao.NoteRoomDao
import com.portugal1576.notescomposemvvm.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao): DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note = note)
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note = note)
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
    }
}