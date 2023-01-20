package com.example.noteappcompose.data

import androidx.room.*
import com.example.noteappcompose.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * from note")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * from note WHERE id = :id")
    suspend fun getNote(id: String) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE from note")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(note: Note)
}