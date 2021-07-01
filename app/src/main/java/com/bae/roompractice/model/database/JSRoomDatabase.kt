package com.bae.roompractice.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bae.roompractice.model.entities.JSUser

@Database(entities = [JSUser::class], version = 1)
abstract class JSRoomDatabase: RoomDatabase()
{
    //데이터베이스와 연결되는 DAO(データベースと接続されるDAO)
    //DAO는 abstract로 "getter"을 제공(DAOはabstractで"getter"を提供)
    abstract fun jsUserDao(): JSUserDao

    companion object {
        @Volatile
        private var INSTANCE: JSRoomDatabase? = null

        fun getDatabase(context: Context): JSRoomDatabase {
            //동시에 2개의 INSTANCE가 생성되는 것을 막기위한 synchronized
            //同時に2つのINSTANCEが生成されるのを防ぐためのsynchronized
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JSRoomDatabase::class.java,
                    "js_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}