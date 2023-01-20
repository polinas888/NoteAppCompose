package com.example.noteappcompose

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.noteappcompose.screen.NoteScreen
import com.example.noteappcompose.screen.NoteViewModel
import com.example.noteappcompose.ui.theme.NoteAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteViewModel by viewModels<NoteViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   NoteApp(noteViewModel)
                }
            }
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NoteApp(noteViewModel: NoteViewModel) {
        //why we need to call collect again, to run just when it been asked or something other
        val notes = noteViewModel.notes.collectAsState().value //collect - To get all the values in the stream as they're emitted
        NoteScreen(notes = notes, onAddNote = { note ->
            noteViewModel.addNote(note)
        }, onDeletedNote = { note ->
            noteViewModel.deleteNote(note)
        })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppComposeTheme {
        NoteScreen(notes = emptyList(), onAddNote = {}, onDeletedNote = {})
    }
}