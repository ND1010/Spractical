package com.dhruv.apps.historyapp.ui.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhruv.apps.historyapp.models.GitUser

class SharedViewModel : ViewModel() {

    val mSList: MutableLiveData<List<GitUser.GitUserItem>> = MutableLiveData()

    fun fetchSelectedUsers(mSList: List<GitUser.GitUserItem>) {
        this.mSList.value = mSList
    }

    fun getSelectedUser(): LiveData<List<GitUser.GitUserItem>>{
        return mSList
    }
}