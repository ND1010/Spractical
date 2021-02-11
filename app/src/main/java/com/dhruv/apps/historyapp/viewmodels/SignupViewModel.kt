package com.dhruv.apps.historyapp.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruv.apps.historyapp.App
import com.dhruv.apps.historyapp.db.LocalDataSource
import com.dhruv.apps.historyapp.db.UserDao
import com.dhruv.apps.historyapp.models.User
import com.dhruv.apps.historyapp.utils.SingleLiveEvent

class SignupViewModel :ViewModel(),LifecycleObserver {
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordC: MutableLiveData<String> = MutableLiveData()
    val signupToApp = SingleLiveEvent<Boolean>()
    val ldbs = LocalDataSource(App.getInstance?.db!!.userDao())


    fun submitSignup(){
        val user = User()
        user.username = username.value.toString()
        user.password = password.value.toString()
        ldbs.insert(user)
        signupToApp.value =true
    }
}