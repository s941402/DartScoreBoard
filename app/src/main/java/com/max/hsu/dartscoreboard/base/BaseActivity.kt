package com.max.hsu.dartscoreboard.base

import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            false
        } else
            super.onKeyDown(keyCode, event)
    }
}