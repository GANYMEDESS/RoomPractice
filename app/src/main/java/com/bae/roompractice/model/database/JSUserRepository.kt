package com.bae.roompractice.model.database

import com.bae.roompractice.model.entities.JSUser
import kotlinx.coroutines.flow.Flow

class JSUserRepository(private val jsUserDao: JSUserDao)
{
    val allUserList: Flow<List<JSUser>> = jsUserDao.getAllUserList()
}