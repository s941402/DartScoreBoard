package com.max.hsu.dartscoreboard.toolUtil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

open class SmartDiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    /**
     * 這裡默認是返回null的，為了避免白光閃爍現象，提供默認實現，返回newItem，必須和[onBindPayloads]一起重寫.
     */
    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return newItem
    }

}