package com.max.hsu.dartscoreboard.view

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.max.hsu.dartscoreboard.adapter.CharactersAdapter
import com.max.hsu.dartscoreboard.adapter.NumberAdapter
import com.max.hsu.dartscoreboard.base.BaseActivity
import com.max.hsu.dartscoreboard.databinding.MainScoreBoardBinding
import com.max.hsu.dartscoreboard.model.NumberModel
import com.max.hsu.dartscoreboard.toolUtil.GridSpaceItemDecoration
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
        scoreViewModel.saveAttackDamage(numberModel)
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