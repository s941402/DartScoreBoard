package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.viewHolder.ItemCharacterVH

class CharactersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemCharacterVH(BindingViewHolder(parent))
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemCharacterVH -> holder.bindView()
        }
    }
}