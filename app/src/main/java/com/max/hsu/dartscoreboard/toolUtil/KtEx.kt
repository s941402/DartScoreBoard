package com.max.hsu.dartscoreboard.toolUtil

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

fun Int.toDp(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
).roundToInt()