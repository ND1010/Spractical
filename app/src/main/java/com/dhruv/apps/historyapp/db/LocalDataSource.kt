package com.dhruv.apps.historyapp.db

import android.util.Log
import com.dhruv.apps.historyapp.models.User
import com.dhruv.apps.historyapp.utils.doBack

/**
 * Created by fizhu on 07,July,2020
 * https://github.com/Fizhu
 */
open class LocalDataSource constructor(
    private val userDao: UserDao
) {
    companion object
    {
        const val TAG = "LocalDataSource"
    }

    fun insert(user: User) {
        doBack(
            action = {
                userDao.insert(user)
            },
            success = { Log.e(TAG,"success insert user to db") },
            error = {  Log.e(TAG,"failed insert user to db") }
        )
    }

    fun getUserByUsernamePassword(username: String, password: String) =
        userDao.getUserByUsernamePassword(username, password)
}