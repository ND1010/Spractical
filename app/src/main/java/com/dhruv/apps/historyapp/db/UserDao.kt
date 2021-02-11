package com.dhruv.apps.historyapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhruv.apps.historyapp.models.User
import io.reactivex.Observable

/**
 * Created by fizhu on 06,July,2020
 * https://github.com/Fizhu
 */

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    fun getUserByUsernamePassword(username: String, password: String): Observable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}