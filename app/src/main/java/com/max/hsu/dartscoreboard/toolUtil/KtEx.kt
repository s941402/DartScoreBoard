package com.max.hsu.dartscoreboard.toolUtil

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
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