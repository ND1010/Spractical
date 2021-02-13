package com.dhruv.apps.historyapp.repositories

import com.dhruv.apps.historyapp.interfaces.NetworkResponseCallback

class RepositoryImpl private constructor() {
    private lateinit var mCallback: NetworkResponseCallback

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

}