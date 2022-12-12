package com.max.hsu.dartscoreboard.view

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.max.hsu.dartscoreboard.adapter.CharactersAdapter
import com.max.hsu.dartscoreboard.adapter.NumberAdapter
import com.max.hsu.dartscoreboard.base.BaseActivity
import com.max.hsu.dartscoreboard.databinding.MainScoreBoardBinding
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.model.RoundType
import com.max.hsu.dartscoreboard.toolUtil.GridSpaceItemDecoration
import com.max.hsu.dartscoreboard.toolUtil.forceToInt
import com.max.hsu.dartscoreboard.toolUtil.toDp
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainScoreBoardActivity : BaseActivity(), ScoreBoardCallBack {

    private lateinit var binding: MainScoreBoardBinding
    private val scoreViewModel: MainScoreBoardViewModel by viewModel()
    private val numberAdapter by lazy { NumberAdapter(this) }
    private val charactersAdapter by lazy { CharactersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
    }

    override fun numberClick(numberModel: NumberModel) {
        when {
            numberModel.isNumber() -> addNumberText(numberModel)
            numberModel.isBack() -> removeNumberText(numberModel)
            numberModel.isConfirm() -> saveNumber(numberModel)
        }
    }

    private fun addNumberText(numberModel: NumberModel) {
        when (numberModel.round) {
            RoundType.ROUND_ONE -> {
                val sb = StringBuilder()
                sb.append(binding.tvMainScoreBoardAttackDamageRoundOneScore.text)
                sb.append(numberModel.realNum)
                binding.tvMainScoreBoardAttackDamageRoundOneScore.text = sb
            }
            RoundType.ROUND_TWO -> {
                val sb = StringBuilder()
                sb.append(binding.tvMainScoreBoardAttackDamageRoundSecondScore.text)
                sb.append(numberModel.realNum)
                binding.tvMainScoreBoardAttackDamageRoundOneScore.text = sb
            }
            else -> {}
        }
    }

    private fun removeNumberText(numberModel: NumberModel) {
        val sb = StringBuilder()
        when (numberModel.round) {
            RoundType.ROUND_ONE -> {
                if (binding.tvMainScoreBoardAttackDamageRoundOneScore.text.isNullOrBlank()) return
                sb.append(binding.tvMainScoreBoardAttackDamageRoundOneScore.text)
                sb.deleteCharAt(sb.length - 1)
                binding.tvMainScoreBoardAttackDamageRoundOneScore.text = sb
            }
            RoundType.ROUND_TWO -> {
                if (binding.tvMainScoreBoardAttackDamageRoundSecondScore.text.isNullOrBlank()) return
                sb.append(binding.tvMainScoreBoardAttackDamageRoundSecondScore.text)
                sb.deleteCharAt(sb.length - 1)
                binding.tvMainScoreBoardAttackDamageRoundOneScore.text = sb
            }
            else -> {}
        }
    }

    private fun saveNumber(numberModel: NumberModel) {
        val score = when (numberModel.round) {
            RoundType.ROUND_ONE -> {
                binding.tvMainScoreBoardAttackDamageRoundOneScore.text.toString().forceToInt()
            }
            RoundType.ROUND_TWO -> {
                binding.tvMainScoreBoardAttackDamageRoundSecondScore.text.toString().forceToInt()
            }
            else -> { 0 }
        }
        scoreViewModel.saveAttackDamage(numberModel.round.roundIndex, score)
    }


    private fun initRecyclerview() {
        binding.rvMainScoreBoardNumber.apply {
            layoutManager = GridLayoutManager(this@MainScoreBoardActivity, 3)
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpaceItemDecoration(
                        3,
                        2.toDp(),
                        false,
                        topSpacing = 0,
                        bottomSpacing = 2.toDp()
                    )
                )
            }
            adapter = numberAdapter
            numberAdapter.submitList(scoreViewModel.getNumberModel())
        }

        binding.rvMainScoreBoardCharacters.apply {
            adapter = charactersAdapter
        }
    }
}