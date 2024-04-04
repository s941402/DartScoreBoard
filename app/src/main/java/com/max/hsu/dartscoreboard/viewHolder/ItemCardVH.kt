package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.databinding.ItemCardInfoBinding
import com.max.hsu.dartscoreboard.model.CardModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack

class ItemCardVH(itemBinding: BindingViewHolder<ItemCardInfoBinding>) :
    BindingViewHolder<ItemCardInfoBinding>(itemBinding.binding) {


    fun bindView(mData: CardModel, listener: ScoreBoardCallBack) = itemView.apply {
        with(binding) {
            val quantity = mData.cardTopic.quantity - mData.answerList.size
            tvItemCardInfoText.text = if (quantity > 0) {
                context.getString(
                    R.string.topicText,
                    mData.cardTopic.topicName,
                    quantity
                )
            } else {
                context.getString(R.string.emptyCard)
            }
            ivItemCardInfoQuestion.setOnClickListener {
                if (quantity > 0) {
                    listener.cardClick(mData, layoutPosition)
                }
            }
        }
    }
}