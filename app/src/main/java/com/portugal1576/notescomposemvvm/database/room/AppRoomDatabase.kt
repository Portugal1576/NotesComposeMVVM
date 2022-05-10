package com.portugal1576.notescomposemvvm.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.portugal1576.notescomposemvvm.database.room.dao.NoteRoomDao
import com.portugal1576.notescomposemvvm.model.Note
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.NOTE_DATDBASE


@Database(entities = [Note::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object{
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase{
            return  if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    NOTE_DATDBASE
                ).build()
                INSTANCE as AppRoomDatabase
            } else INSTANCE as AppRoomDatabase
        }
    }
}