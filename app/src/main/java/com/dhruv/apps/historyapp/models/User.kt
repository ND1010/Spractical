package com.dhruv.apps.historyapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Expose @SerializedName("id")
    var id: Int? = 0,
    @Expose @SerializedName("username")
    var username: String = "",
    @Expose @SerializedName("password")
    var password: String= ""
)