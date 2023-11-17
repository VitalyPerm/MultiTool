package ru.kvf.multitool.core

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        private const val TAG = "check___"

        fun print(message: String) {
            Log.d(TAG, message)
        }

        fun toast(message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        lateinit var context: App
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}