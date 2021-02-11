package com.dhruv.apps.historyapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.dhruv.apps.historyapp.db.Db

/**
 * Created by fizhu on 06,July,2020
 * https://github.com/Fizhu
 */

class App : Application() {

    lateinit var db: Db

    override fun onCreate() {
        super.onCreate()
        context = this
        singleton = this

        db = Db.getInstance(this)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
                /**
                 * The application [Context] made static.
                 * Do **NOT** use this as the context for a view,
                 * this is mostly used to simplify calling of resources
                 * (esp. String resources) outside activities.
                 */
        var context: Context? = null
            private set

        @SuppressLint("StaticFieldLeak")
        var singleton: App? = null
            private set

        val getInstance: App?
            get() = singleton

        val spUser: SharedPreferences
            get() = getInstance!!.getSharedPreferences("myUserPref", Context.MODE_PRIVATE)
    }

}