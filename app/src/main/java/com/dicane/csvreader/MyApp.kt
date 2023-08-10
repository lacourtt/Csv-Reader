package com.dicane.csvreader

import android.app.Application
import com.dicane.csvreader.di.databaseModule
import com.dicane.csvreader.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApp)
            modules(listOf(databaseModule, viewModelModule))
        }
    }
}