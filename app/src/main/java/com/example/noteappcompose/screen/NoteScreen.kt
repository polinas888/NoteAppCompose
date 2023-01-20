package com.example.noteappcompose.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteappcompose.R
import com.example.noteappcompose.components.InputContent
import com.example.noteappcompose.components.SaveButton
import com.example.noteappcompose.model.Note
import com.example.noteappcompose.util.formatDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onDeletedNote: (Note) -> Unit
) {
    val title = remember {
        mutableStateOf("")
    }

    val description = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        //TopAppBar
        TopAppBar(
            backgroundColor = Color.LightGray,
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Icon notification"
                )
            }
        )
        //Input content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputContent(
                text = title.value,
                onTextChange = {
                    title.value = it
                }, label = "Title",
                modifier = Modifier.padding(top = 9.dp, bottom = 9.dp)
            )
            InputContent(text = description.value, onTextChange = {
                description.value = it
            }, label = "add a note", modifier = Modifier.padding(top = 9.dp, bottom = 9.dp))
            // save note button
            SaveButton(text = "Save", modifier = Modifier.padding(top = 9.dp)) {
                if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                    onAddNote(Note(title = title.value, description = description.value))
                    title.value = ""
                    description.value = ""
                    Toast.makeText(context, "Note saved!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Divider(modifier = Modifier.padding(10.dp))
        //notes list
        LazyColumn {
            items(items = notes) { note ->
                NoteItem(note = note, onClickNote = { onDeletedNote(note) })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteItem(note: Note, onClickNote: (Note) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .clickable {
            }, color = Color.LightGray
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .fillMaxWidth()
                .clickable {
                    onClickNote(note)
                }) {
            Text(text = note.title, style = MaterialTheme.typography.subtitle2)
            Text(text = note.description, style = MaterialTheme.typography.subtitle1)
            Text(text = formatDate(note.entryData.time), style = MaterialTheme.typography.caption)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(notes = emptyList(), onAddNote = {}, onDeletedNote = {})
}