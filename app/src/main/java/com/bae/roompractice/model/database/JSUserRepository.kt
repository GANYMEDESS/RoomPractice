package com.bae.roompractice.model.database

import androidx.annotation.WorkerThread
import com.bae.roompractice.model.entities.JSUser
import kotlinx.coroutines.flow.Flow

class JSUserRepository(private val jsUserDao: JSUserDao)
{
    val allUserList: Flow<List<JSUser>> = jsUserDao.getAllUserList()

    // 메소드(Method)가 WorkerThread에서 동작
    // メソッドが WorkerThreadで動作
    @WorkerThread
    suspend fun insertUserData(jsUser: JSUser) {
        jsUserDao.insertUser(jsUser)
    }
}