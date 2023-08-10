package com.dicane.csvreader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicane.csvreader.model.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase(){
    abstract fun ContactDao() : ContactDao
}