package com.dhruv.apps.historyapp.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruv.apps.historyapp.App
import com.dhruv.apps.historyapp.db.LocalDataSource
import com.dhruv.apps.historyapp.utils.SingleLiveEvent
import com.dhruv.apps.historyapp.utils.route
import com.fizhu.bikeappconcept.data.pref.PrefDataSource
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel :ViewModel(),LifecycleObserver {
    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val loginToApp = SingleLiveEvent<Boolean>()
    val ldbs = LocalDataSource(App.getInstance?.db!!.userDao())
    protected val compositeDisposable by lazy { CompositeDisposable() }
    val userPref =PrefDataSource()
    var isLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun getValue() {
        isLogin.postValue(userPref.getIsLogin())
    }

    fun submitLogin(){
        compositeDisposable.route(ldbs.getUserByUsernamePassword(username.value.toString().trim(),password.value.toString().trim()),
            io = {
                if (it.isNotEmpty()) {
                    userPref.setIsLogin(true)
                    loginToApp.postValue(true)
                } else {
                    loginToApp.postValue(false)
                }
            },
            error = {
                loginToApp.postValue(false)
            }
        )

    }
}