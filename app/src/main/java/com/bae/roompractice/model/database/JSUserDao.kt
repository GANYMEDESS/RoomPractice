package com.bae.roompractice.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bae.roompractice.model.entities.JSUser
import kotlinx.coroutines.flow.Flow

@Dao
interface JSUserDao
{
    @Query("SELECT * FROM JS_USER_TABLE ORDER BY ID")
    fun getAllUserList(): Flow<List<JSUser>>

    @Insert
    suspend fun insertUser(jsUser: JSUser)
}