package com.example.newspal

import android.app.Application
import android.content.Context
import com.example.newspal.data.DBHelper

class NewsApplication : Application(){
    private var mContext: Context? = null
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }
    val database: DBHelper by lazy { DBHelper.getDatabase(this) }
    fun getContext(): Context? {
        return mContext
    }

}
