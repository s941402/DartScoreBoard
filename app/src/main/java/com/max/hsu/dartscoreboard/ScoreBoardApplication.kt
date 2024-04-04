package com.max.hsu.dartscoreboard

import android.app.Application
import com.max.hsu.dartscoreboard.koinDI.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ScoreBoardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger()
            androidContext(this@ScoreBoardApplication)
            // Load modules
            injectFeature()
        }
    }
}