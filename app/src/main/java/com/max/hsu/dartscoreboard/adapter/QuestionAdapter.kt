package com.max.hsu.dartscoreboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.max.hsu.dartscoreboard.model.QuestionModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.SmartDiffCallback
import com.max.hsu.dartscoreboard.view.question.QuestionViewCallback
import com.max.hsu.dartscoreboard.viewHolder.ItemQuestionVH

class QuestionAdapter (
    private val callback: QuestionViewCallback
) : ListAdapter<QuestionModel, RecyclerView.ViewHolder>(SmartDiffCallback<QuestionModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemQuestionVH(BindingViewHolder(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemQuestionVH -> holder.bind(mData = getItem(position), callback)
        }
    }
}