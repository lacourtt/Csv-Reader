package com.dicane.csvreader.di

import androidx.room.Room
import com.dicane.csvreader.database.ContactDao
import com.dicane.csvreader.database.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            RoomDatabase::class.java,
            "contact_database"
        )
            .allowMainThreadQueries()
            .build()
    }
    single<ContactDao> { get<RoomDatabase>().ContactDao() }
}