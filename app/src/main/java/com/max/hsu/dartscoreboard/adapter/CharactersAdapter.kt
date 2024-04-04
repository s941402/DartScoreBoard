package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.model.CharactersModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.SmartDiffCallback
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack
import com.max.hsu.dartscoreboard.viewHolder.ItemCharacterVH

class CharactersAdapter(private val listener: ScoreBoardCallBack) :
    ListAdapter<CharactersModel, RecyclerView.ViewHolder>(SmartDiffCallback<CharactersModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCharacterVH(BindingViewHolder(parent))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemCharacterVH -> holder.bindView(getItem(position),listener)
        }
    }
}