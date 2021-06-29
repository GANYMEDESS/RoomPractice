package com.bae.roompractice.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "js_user_table")
data class JSUser(
    var name: String,
    var age: String,
    var phone: String,
    var sex: String,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
