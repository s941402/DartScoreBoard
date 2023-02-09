package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.databinding.ItemQuestionChooseBinding
import com.max.hsu.dartscoreboard.model.QuestionModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.view.question.QuestionViewCallback

class ItemQuestionVH(itemBinding: BindingViewHolder<ItemQuestionChooseBinding>) :
    BindingViewHolder<ItemQuestionChooseBinding>(itemBinding.binding) {

    fun bind(mData: QuestionModel, callback: QuestionViewCallback) = itemView.apply {
        with(binding) {
            clItemQuestionChooseMain.isSelected = mData.isSelected
            tvItemQuestionChooseText.text =
                context.getString(R.string.answerNo, layoutPosition + 1, mData.answerText)
            itemView.setOnClickListener {
                // 單選 如果點擊已被選擇則不用改變
                if (!mData.isSelected) callback.changeSelectedAnswer(mData, layoutPosition)
            }
        }
    }
}