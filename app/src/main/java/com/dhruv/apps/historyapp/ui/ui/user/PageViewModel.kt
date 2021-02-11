package com.dhruv.apps.historyapp.ui.ui.user

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dhruv.apps.historyapp.interfaces.NetworkResponseCallback
import com.dhruv.apps.historyapp.models.GitUser
import com.dhruv.apps.historyapp.repositories.RepositoryImpl
import com.dhruv.apps.historyapp.utils.NetworkHelper

class PageViewModel : ViewModel() {

    private var mRepository = RepositoryImpl.getInstance()
    private var mList: MutableLiveData<List<GitUser.GitUserItem>> = MutableLiveData()
    private var mSList: MutableLiveData<List<GitUser.GitUserItem>> = MutableLiveData()
    var mShowProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowApiError: MutableLiveData<Boolean> = MutableLiveData()
    private var mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun fetchGitUsers(context: Context,per_page :String,since:String): MutableLiveData<List<GitUser.GitUserItem>> {
        if (NetworkHelper.isOnline(context)) {
            mShowProgressBar.value = true
            mList = mRepository.getGitUsers(per_page,since,object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = true
                }

                override fun onNetworkSuccess() {
                    Log.e("TAG", "onNetworkSuccess")
                    mShowProgressBar.value = false
                }
            })
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun fetchSelectedUsers(): MutableLiveData<List<GitUser.GitUserItem>> {
        var sListData :List<GitUser.GitUserItem>?
        mList.value.let {
            sListData = mList.value?.filter {
                it.isChecked
            }
        }
        sListData?.let {
            mSList.value = sListData
            Log.e("TAG", "fetchSelectedUsers: ${mSList.value.toString()}")
        }
        return mSList
    }
}