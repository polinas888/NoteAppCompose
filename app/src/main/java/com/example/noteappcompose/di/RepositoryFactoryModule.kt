package com.example.noteappcompose.di

import com.example.noteappcompose.data.NoteDatabaseDao
import com.example.noteappcompose.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryFactoryModule {

        @Provides
        fun provideNoteRepository(noteDatabaseDao: NoteDatabaseDao): NoteRepository {
            return NoteRepository(noteDatabaseDao)
        }
}