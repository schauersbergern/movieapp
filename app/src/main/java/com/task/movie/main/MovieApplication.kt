package com.task.movie.main

import android.app.Application
import movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        insertKoin()
    }

    private fun insertKoin() {
        startKoin {
            androidContext(this@MovieApplication)
            modules(listOf(movieModule))
        }
    }
}