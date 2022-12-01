package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.databinding.ItemScoreNumberBinding
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack

class ItemNumberVH(itemBinding: BindingViewHolder<ItemScoreNumberBinding>) :
    BindingViewHolder<ItemScoreNumberBinding>(itemBinding.binding) {


    fun bindView(mData: NumberModel, listener: ScoreBoardCallBack) = itemView.apply {
        with(binding) {
            btnItemScoreNumber.text = mData.displayText
            btnItemScoreNumber.setOnClickListener {
                listener.numberClick(mData)
            }
        }
    }
}