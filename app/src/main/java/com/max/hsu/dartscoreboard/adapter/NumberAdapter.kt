package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.viewHolder.ItemNumberVH

class NumberAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemNumberVH(BindingViewHolder(parent))
    }

    override fun getItemCount(): Int = 12

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemNumberVH -> holder.bindView()
        }
    }
}