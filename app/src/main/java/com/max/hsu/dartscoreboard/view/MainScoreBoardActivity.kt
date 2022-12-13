package com.max.hsu.dartscoreboard.view

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.adapter.CardAdapter
import com.max.hsu.dartscoreboard.adapter.CharactersAdapter
import com.max.hsu.dartscoreboard.adapter.NumberAdapter
import com.max.hsu.dartscoreboard.base.BaseActivity
import com.max.hsu.dartscoreboard.databinding.MainScoreBoardBinding
import com.max.hsu.dartscoreboard.model.CardModel
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
    private val cardAdapter by lazy { CardAdapter(this) }
    private val charactersAdapter by lazy { CharactersAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainScoreBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        setObserver()
        initData()
    }

    override fun numberClick(numberModel: NumberModel) {
        when {
            numberModel.isNumber() -> addNumberText(numberModel)
            numberModel.isBack() -> removeNumberText(numberModel)
            numberModel.isConfirm() -> saveNumber(numberModel)
        }
    }

    override fun cardClick(cardModel: CardModel) {
        showAlert()
    }

    private fun initData() {
        scoreViewModel.getCharactersModel()
        scoreViewModel.getCardsModel()
        scoreViewModel.getNumberModel(RoundType.ROUND_ONE)
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
                binding.tvMainScoreBoardAttackDamageRoundSecondScore.text = sb
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
                binding.tvMainScoreBoardAttackDamageRoundSecondScore.text = sb
            }
            else -> {}
        }
    }

    private fun saveNumber(numberModel: NumberModel) {
        when (numberModel.round) {
            RoundType.ROUND_ONE -> {
                val score =
                    binding.tvMainScoreBoardAttackDamageRoundOneScore.text.toString().forceToInt()
                scoreViewModel.saveAttackDamage(numberModel.round.roundIndex, score)
                scoreViewModel.goRoundTwo()
            }
            RoundType.ROUND_TWO -> {
                val score = binding.tvMainScoreBoardAttackDamageRoundSecondScore.text.toString()
                    .forceToInt()
                scoreViewModel.saveAttackDamage(numberModel.round.roundIndex, score)
                scoreViewModel.goRoundTwo()
            }
            else -> {}
        }
    }

    private fun changeSelection(round: RoundType) {
        if (round == RoundType.ROUND_ONE) {
            binding.tvMainScoreBoardAttackDamageRoundOneScore.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rect_1a000000_stroke_black_1)
            binding.tvMainScoreBoardAttackDamageRoundSecondScore.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rect_white_stroke_black_1)
        } else {
            binding.tvMainScoreBoardAttackDamageRoundOneScore.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rect_white_stroke_black_1)
            binding.tvMainScoreBoardAttackDamageRoundSecondScore.background =
                ContextCompat.getDrawable(this, R.drawable.bg_rect_1a000000_stroke_black_1)
        }
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
            itemAnimator = null
            adapter = numberAdapter
        }
        initCharacterList()
        initCardList()
    }

    private fun initCharacterList() {
        binding.rvMainScoreBoardCharacters.apply {
            layoutManager = GridLayoutManager(this@MainScoreBoardActivity, 4)
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpaceItemDecoration(
                        4,
                        2.toDp(),
                        false,
                        topSpacing = 0,
                        bottomSpacing = 0
                    )
                )
            }
            itemAnimator = null
            adapter = charactersAdapter
        }
    }


    private fun initCardList() {
        binding.rvMainScoreBoardCard.apply {
            layoutManager = GridLayoutManager(this@MainScoreBoardActivity, 4)
            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpaceItemDecoration(
                        4,
                        2.toDp(),
                        false,
                        topSpacing = 0,
                        bottomSpacing = 0
                    )
                )
            }
            itemAnimator = null
            adapter = cardAdapter
        }
    }

    private fun setObserver() {
        scoreViewModel.numberResult.observe(this) {
            changeSelection(it.getOrNull(0)?.round ?: RoundType.ROUND_ONE)
            numberAdapter.submitList(it)
        }

        scoreViewModel.charactersResult.observe(this) {
            charactersAdapter.submitList(it)
        }

        scoreViewModel.cardResult.observe(this) {
            cardAdapter.submitList(it)
        }
    }

    private fun showAlert() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogMessage))
            .setNegativeButton(resources.getString(R.string.dialogCancel)) { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.dialogConfirm)) { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }
}