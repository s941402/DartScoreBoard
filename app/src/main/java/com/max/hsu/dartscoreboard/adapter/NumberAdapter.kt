package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.SmartDiffCallback
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack
import com.max.hsu.dartscoreboard.viewHolder.ItemNumberVH

class NumberAdapter(private val listener : ScoreBoardCallBack) : ListAdapter<NumberModel, RecyclerView.ViewHolder>(SmartDiffCallback<NumberModel>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemNumberVH(BindingViewHolder(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemNumberVH -> holder.bindView(getItem(position),listener)
        }
    }
}