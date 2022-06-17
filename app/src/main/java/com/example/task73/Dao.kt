package com.example.task73

import androidx.room.*
import androidx.room.Dao


@Dao
interface Dao {

    @Query("SELECT * FROM EntityImages")
    fun getAll(): List<EntityImages?>?

    @Query("SELECT * from EntityImages WHERE id = :id")
    fun getItemById(id: Int): List<EntityImages?>?

    @Insert
    fun insert(entityImages: EntityImages?)

    @Update
    fun update(entityImages: EntityImages?)

    @Delete
    fun delete(entityImages: EntityImages?)

    @Query("DELETE FROM EntityImages")
    fun clearTable()

    @Insert
    fun insertUpdate(entityUpdate: EntityUpdate?)

    @Update
    fun updateUpdate(entityUpdate: EntityUpdate?)

    @Query("SELECT * from EntityUpdate WHERE id = :id")
    fun getUpdateById(id: Int): List<EntityUpdate?>?
}