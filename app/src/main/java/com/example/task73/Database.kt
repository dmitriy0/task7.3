package com.example.task73

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [EntityImages::class, EntityUpdate::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao?
}