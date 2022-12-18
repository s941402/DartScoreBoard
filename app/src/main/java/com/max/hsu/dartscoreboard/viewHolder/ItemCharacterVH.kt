package com.max.hsu.dartscoreboard.viewHolder

import androidx.core.view.isVisible
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.databinding.ItemCharacterInfoBinding
import com.max.hsu.dartscoreboard.model.CharactersModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME

class ItemCharacterVH(itemBinding: BindingViewHolder<ItemCharacterInfoBinding>) :
    BindingViewHolder<ItemCharacterInfoBinding>(itemBinding.binding) {


    fun bindView(mData: CharactersModel) = itemView.apply {
        with(binding) {
            tvItemCharacterInfoBlood.text = context.getString(
                R.string.bloodVolume,
                mData.currentBlood,
                TOTAL_BLOOD_VOLUME
            )
            ivItemCharacterInfoSelected.isVisible = mData.isMaster
            cpvItemCharacterInfoBloodCircle.setProgress(mData.currentBlood * 100f / TOTAL_BLOOD_VOLUME)
        }
    }
}