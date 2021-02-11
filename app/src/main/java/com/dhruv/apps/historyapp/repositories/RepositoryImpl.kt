package com.dhruv.apps.historyapp.repositories

import androidx.lifecycle.MutableLiveData
import com.dhruv.apps.historyapp.interfaces.NetworkResponseCallback
import com.dhruv.apps.historyapp.models.Country
import com.dhruv.apps.historyapp.models.GitUser
import com.dhruv.apps.historyapp.network.RestClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mGitUserList: MutableLiveData<List<GitUser.GitUserItem>> =
        MutableLiveData()


    companion object {
        private var mInstance: RepositoryImpl? = null
        fun getInstance(): RepositoryImpl {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = RepositoryImpl()
                }
            }
            return mInstance!!
        }
    }


    private lateinit var mGitUserCall: Call<GitUser>
    fun getGitUsers(per_page :String,since:String,callback: NetworkResponseCallback): MutableLiveData<List<GitUser.GitUserItem>> {
        mCallback = callback
        mGitUserCall = RestClient.getInstance().getApiService().getUser(per_page,since)
        mGitUserCall.enqueue(object : Callback<GitUser> {

            override fun onResponse(call: Call<GitUser>, response: Response<GitUser>) {
                mGitUserList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<GitUser>, t: Throwable) {
                mGitUserList.value = ArrayList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mGitUserList
    }
}