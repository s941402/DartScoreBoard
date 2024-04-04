package com.max.hsu.dartscoreboard.base

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel

open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = this.getApplication<Application>()
    protected val resources: Resources = mContext.applicationContext.resources
}