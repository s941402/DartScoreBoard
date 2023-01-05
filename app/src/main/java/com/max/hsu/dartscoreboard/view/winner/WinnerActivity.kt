package com.max.hsu.dartscoreboard.view.winner

import android.os.Bundle
import android.view.KeyEvent
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.base.BaseActivity
import com.max.hsu.dartscoreboard.databinding.WinnerDashBoardBinding

class WinnerActivity : BaseActivity() {

    private lateinit var binding: WinnerDashBoardBinding
    private val teamArr = arrayListOf("一", "二", "三", "四")

    private val position by lazy {
        intent.extras?.getInt("position") ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WinnerDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvForumSecretBaseText.text = getString(R.string.winnerTeam, teamArr[position])
    }

}