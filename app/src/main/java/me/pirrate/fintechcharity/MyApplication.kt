package me.pirrate.fintechcharity

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Globals.restore(this)
    }
}