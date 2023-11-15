package ru.kvf.multitool.core

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        private const val TAG = "check___"

        fun print(message: String) {
            Log.d(TAG, message)
        }
    }
}