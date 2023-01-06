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
            val blood = when {
                mData.currentBlood >= 500 -> 500
                mData.currentBlood <= 0 -> 0
                else -> mData.currentBlood
            }
            sivItemCharacterInfoImage.setImageResource(mData.iconHeadDrawable)
            tvItemCharacterInfoBlood.text = context.getString(
                R.string.bloodVolume,
                blood,
                TOTAL_BLOOD_VOLUME
            )
            if (mData.isSelected) {
                clItemCharacterInfoRoot.setBackgroundResource(R.drawable.bg_rect_white_stroke_red_1)
                lavItemCharacterInfoAttack.visible(true)
            } else {
                clItemCharacterInfoRoot.setBackgroundResource(0)
                lavItemCharacterInfoAttack.visible(false)
            }
            ivItemCharacterInfoDeath.visible(mData.isDeath || blood <= 0)
            ivItemCharacterInfoSelected.isVisible = mData.isMaster
            cpvItemCharacterInfoBloodCircle.setProgress(
                blood * 100f / TOTAL_BLOOD_VOLUME,
                animate = true
            )

            clItemCharacterInfoRoot.setOnClickListener {
                if (mData.canSelect && !mData.isMaster) listener.changeChooseCharacters(
                    mData,
                    layoutPosition
                )
            }
        }
    }
}