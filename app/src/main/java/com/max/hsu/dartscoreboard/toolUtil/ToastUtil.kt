package com.max.hsu.dartscoreboard.toolUtil

import android.content.Context
import android.view.Gravity
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    /**
     * 顯示Toast
     * @param context 上下文
     * @param content 要顯示的内容
     */
    fun showStringToast(
        context: Context,
        content: String,
        useShort: Boolean = true,
        gravity: Int = Gravity.CENTER
    ) {
        if (toast == null) {
            toast = Toast.makeText(
                context,
                content,
                if (useShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            )
        } else {
            toast?.setText(content)
        }
        toast?.setGravity(gravity, 0, 0)
        toast?.show()
    }

    /**
     * 顯示Toast
     * @param context 上下文
     * @param resId 要顯示的資源id
     */
    fun showResToast(context: Context, resId: Int) {
        showStringToast(context, context.resources.getString(resId))
    }

}