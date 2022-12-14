package com.max.hsu.dartscoreboard.toolUtil

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

fun Int.toDp(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
).roundToInt()

/** 字串轉Int 會先判斷是否為數字(防爆錯)
 *  @param default 如轉換失敗預設為0 或可以自己帶入
 * */
fun String?.forceToInt(default: Int = 0): Int {
    if (this.isNullOrBlank()) return default
    return try {
        if (this.filter { it.isDigit() }.any { this.isNotEmpty() }) {
            toInt()
        } else {
            default
        }
    } catch (ex: NumberFormatException) {
        default
    }
}

/**
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

fun Context.getDrawableCompat(drawableResId: Int) = ContextCompat.getDrawable(this, drawableResId)

@Suppress("UNCHECKED_CAST")
fun <T> Activity.lazyExtra(name: String, defaultValue: T): Lazy<T> {
    return lazy {
        when (defaultValue) {
            is String -> intent.getStringExtra(name) as T ?: defaultValue
            is Int -> intent.getIntExtra(name, defaultValue) as T
            is Boolean -> intent.getBooleanExtra(name, defaultValue) as T
            else -> throw RuntimeException()
        }
    }
}

fun TextView.textColor(@ColorRes color: Int) {
    setTextColor(context.getColorCompat(color))
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delayTime Long 延迟时间，默认800毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(delayTime: Long = 500, block: (View) -> Unit) {
    triggerDelay = delayTime
    setOnClickListener {
        if (clickEnable()) {
            block(it)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

/** 介面顯示或隱藏
 *  @param visible 是否隱藏
 *  @param useGone 可帶可不帶(true: 預設直 Gone; false: Invisible)
 * */
fun View.visible(visible: Boolean, useGone: Boolean = true) {
    this.visibility = if (visible) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}
