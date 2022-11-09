package com.example.jetnote.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.jetnote.model.Note
import com.example.jetnote.util.DateConverter
import com.example.jetnote.util.UIUDConvertor

@Database(entities =[Note::class], version = 1, exportSchema = false )
@TypeConverters(DateConverter::class,UIUDConvertor::class)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun noteDao():NoteDatabaseDao
}