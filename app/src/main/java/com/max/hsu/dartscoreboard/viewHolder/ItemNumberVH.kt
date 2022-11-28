package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.databinding.ItemScoreNumberBinding
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder

class ItemNumberVH(itemBinding: BindingViewHolder<ItemScoreNumberBinding>) :
    BindingViewHolder<ItemScoreNumberBinding>(itemBinding.binding) {


    fun bindView() = itemView.apply {
        with(binding) {
            val number = layoutPosition + 1
            btnItemScoreNumber.text = "$number"
            btnItemScoreNumber.setOnClickListener {

            }
        }
    }
}