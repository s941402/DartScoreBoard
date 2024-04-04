package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.model.CardModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.SmartDiffCallback
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack
import com.max.hsu.dartscoreboard.viewHolder.ItemCardVH

class CardAdapter(private val listener: ScoreBoardCallBack) :
    ListAdapter<CardModel, RecyclerView.ViewHolder>(
        SmartDiffCallback<CardModel>()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCardVH(BindingViewHolder(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemCardVH -> holder.bindView(getItem(position), listener)
        }
    }
}