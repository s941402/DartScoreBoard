package com.max.hsu.dartscoreboard.viewHolder

import androidx.core.view.isVisible
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.databinding.ItemCharacterInfoBinding
import com.max.hsu.dartscoreboard.model.CharactersModel
import com.max.hsu.dartscoreboard.toolUtil.BindingViewHolder
import com.max.hsu.dartscoreboard.toolUtil.TOTAL_BLOOD_VOLUME
import com.max.hsu.dartscoreboard.toolUtil.visible
import com.max.hsu.dartscoreboard.view.ScoreBoardCallBack

class ItemCharacterVH(itemBinding: BindingViewHolder<ItemCharacterInfoBinding>) :
    BindingViewHolder<ItemCharacterInfoBinding>(itemBinding.binding) {


    fun bindView(mData: CharactersModel, listener: ScoreBoardCallBack) = itemView.apply {
        with(binding) {
            tvItemCharacterInfoBlood.text = context.getString(
                R.string.bloodVolume,
                mData.currentBlood,
                TOTAL_BLOOD_VOLUME
            )
            clItemCharacterInfoRoot.setBackgroundResource(if (mData.isSelected) R.drawable.bg_rect_white_stroke_red_1 else 0)
            ivItemCharacterInfoDeath.visible(mData.isDeath || mData.currentBlood <= 0)
            ivItemCharacterInfoSelected.isVisible = mData.isMaster
            cpvItemCharacterInfoBloodCircle.setProgress(
                mData.currentBlood * 100f / TOTAL_BLOOD_VOLUME,
                animate = true
            )


            if (mData.canSelect) {
                clItemCharacterInfoRoot.setOnClickListener {
                    if (!mData.isMaster) listener.changeChooseCharacters(mData, layoutPosition)
                }
            }
        }
    }
}