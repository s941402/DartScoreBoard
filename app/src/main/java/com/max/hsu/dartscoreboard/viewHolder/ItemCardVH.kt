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
            tvItemCardInfoText.text =
                context.getString(R.string.topicText, mData.typeText, mData.quantity)
            ivItemCardInfoQuestion.setOnClickListener {
                listener.cardClick(mData)
            }
        }
    }
}