package com.portugal1576.notescomposemvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.portugal1576.notescomposemvvm.database.room.AppRoomDatabase
import com.portugal1576.notescomposemvvm.database.room.dao.repository.RoomRepository
import com.portugal1576.notescomposemvvm.model.Note
import com.portugal1576.notescomposemvvm.utils.REPOSITORY
import com.portugal1576.notescomposemvvm.utils.TYPE_FIREBASE
import com.portugal1576.notescomposemvvm.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    fun initDatabase(type: String, onSucces: ()-> Unit) {
        Log.d("checkData", "MainViewModel init Database with type: $type")
        when(type){
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSucces()
            }
        }
    }
    fun addNote(note: Note, onSucces: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSucces()
                }
            }
        }
    }

    fun updateNote(note: Note, onSucces: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.update(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSucces()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSucces: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSucces()
                }
            }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}