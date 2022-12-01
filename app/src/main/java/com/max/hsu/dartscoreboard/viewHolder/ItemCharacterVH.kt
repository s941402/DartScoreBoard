package com.max.hsu.dartscoreboard.viewHolder

import com.max.hsu.dartscoreboard.databinding.ItemCharacterInfoBinding
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder

class ItemCharacterVH(itemBinding: BindingViewHolder<ItemCharacterInfoBinding>) :
    BindingViewHolder<ItemCharacterInfoBinding>(itemBinding.binding) {


    fun bindView() = itemView.apply {
        with(binding) {
            cpvItemCharacterInfoBloodCircle.setProgress((0..100).random().toFloat())
        }
    }
}