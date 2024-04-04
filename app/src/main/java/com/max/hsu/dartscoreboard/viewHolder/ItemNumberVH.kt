package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.databinding.ItemScoreNumberBinding
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.model.RoundType
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.getColorCompat
import com.max.hsu.dartscoreboard.toolUtil.getDrawableCompat
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack

class ItemNumberVH(itemBinding: BindingViewHolder<ItemScoreNumberBinding>) :
    BindingViewHolder<ItemScoreNumberBinding>(itemBinding.binding) {


    fun bindView(mData: NumberModel, listener: ScoreBoardCallBack) = itemView.apply {
        with(binding) {
            btnItemScoreNumber.apply {
                text = mData.displayText
                background = if (mData.isNumber()) {
                    context.getDrawableCompat(R.drawable.bg_rect_white_radius_4_stroke_black_2)
                } else {
                    context.getDrawableCompat(R.drawable.bg_rect_orange_radius_4_stroke_black_2)
                }
                setOnClickListener {
                    listener.numberClick(mData)
                }
            }
        }
    }
}