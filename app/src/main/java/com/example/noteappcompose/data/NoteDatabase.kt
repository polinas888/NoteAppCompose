package com.example.noteappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteappcompose.model.Note
import com.example.noteappcompose.util.DateConverter
import com.example.noteappcompose.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false) //entities - tables
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}