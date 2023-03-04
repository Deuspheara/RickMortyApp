package fr.deuspheara.rickandmorty

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickMortyApp : Application() {

    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }

        fun applicationContext(): Application {
            return appContext as Application
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}