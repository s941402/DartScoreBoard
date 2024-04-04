package com.max.hsu.dartscoreboard.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent

open class BaseViewModel : ViewModel(), KoinComponent {

    /**
     * This is a scope for all coroutines launched by [BaseViewModel]
     * that will be dispatched in Main Thread
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main)

    /**
     * This is a scope for all co-routines launched by [BaseViewModel]
     * that will be dispatched in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.Default)

}