package com.example.task73

import android.app.Application
import androidx.room.Room


class App : Application() {
    private var database: Database? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, Database::class.java, "database")
            .build()
    }

    fun getDatabase(): Database? {
        return database
    }

    companion object {
        var instance: App? = null
    }
}