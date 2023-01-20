package com.example.noteappcompose.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappcompose.model.Note
import com.example.noteappcompose.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository): ViewModel() {
    private var _notes = MutableStateFlow<List<Note>>(emptyList()) // stateFlow with default value, sharedFlow without, both hot flow - emit value when there are subscribers
    val notes = _notes.asStateFlow() // only for read

    init {
        viewModelScope.launch {
            repository.getNotes().distinctUntilChanged().collect { listNotes ->  //distinctUntilChanged - filter out all identical values
                if (listNotes.isEmpty()) {
                    Log.d("Empty", "Empty list")
                } else {
                    _notes.value = listNotes
                }
            }
        }
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }
    fun update(note: Note) = viewModelScope.launch { repository.updateNote(note) }
}