package com.portugal1576.notescomposemvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.portugal1576.notescomposemvvm.utils.Constans.Keys.NOTES_TABLE

//анотации нужны для работы с Room
@Entity(tableName = NOTES_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val subtitle: String
)
