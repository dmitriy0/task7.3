package com.example.task73


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EntityImages {
    @PrimaryKey
    var id: Int = 0
    var imageCat: String? = null
}


@Entity
class EntityUpdate {
    @PrimaryKey
    var id: Int = 0
    var needUpdate: Boolean = false
}