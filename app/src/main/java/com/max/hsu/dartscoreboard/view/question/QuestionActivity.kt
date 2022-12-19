package com.max.hsu.dartscoreboard.view.question

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.max.hsu.dartscoreboard.R
import com.max.hsu.dartscoreboard.adapter.QuestionAdapter
import com.max.hsu.dartscoreboard.base.BaseActivity
import com.max.hsu.dartscoreboard.databinding.QuestionBoardBinding
import com.max.hsu.dartscoreboard.model.CardModel
import com.max.hsu.dartscoreboard.model.CardTopic
import com.max.hsu.dartscoreboard.model.QuestionModel
import com.max.hsu.dartscoreboard.toolUtil.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class QuestionActivity : BaseActivity(), QuestionViewCallback {

    private lateinit var binding: QuestionBoardBinding
    private val questionViewModel: QuestionViewModel by viewModel()
    private val questionAdapter by lazy {
        QuestionAdapter(this)
    }
    private var mTimer: CountDownTimer? = null

    private val cardModel by lazy {
        val selectedStr = intent.extras?.getString("questionModel") ?: ""
        if (selectedStr.isBlank()) {
            CardModel(0, CardTopic.Program)
        } else {
            Gson().fromJsonExtend(selectedStr)
        }
    }
    private val position by lazy {
        intent.extras?.getInt("position") ?: -1
    }

    private var questionId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QuestionBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initQuestionList()
        setObserver()
        getInitData()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
        }
    }

    private fun initView() {
        if (mTimer == null) {
            mTimer = object : CountDownTimer(12000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvQuestionBoardTime.apply {
                        val second = millisUntilFinished / 1000
                        text = "$second"
                        textColor(if (second <= 3) R.color.color_eb5a5a else R.color.black)
                    }
                    // logic to set the EditText could go here
                }

                override fun onFinish() {
                    binding.tvQuestionBoardTime.text = getString(R.string.countdownTimesUp)
                }
            }.start()
        }

        binding.btnQuestionBoardConfirm.clickWithTrigger {
            if (!questionViewModel.hasSelectedAnswer()) {
                showToast("尚未選答案")
            } else {
                showAlert()
            }
        }

        binding.clQuestionBoardAnswer.setOnClickListener { }
    }


    override fun changeSelectedAnswer(item: QuestionModel, position: Int) {
        changeAnswerData(position)
    }

    private fun initQuestionList() {
        binding.rvQuestionBoardList.apply {
            itemAnimator = null
            adapter = questionAdapter
        }
    }

    private fun setObserver() {
        questionViewModel.topicQuestionResult.observe(this) {
            binding.tvQuestionBoardQuestion.text = it.title
            questionId = it.id
        }

        questionViewModel.questionResult.observe(this) {
            questionAdapter.submitList(it)
        }

        questionViewModel.errorResult.observe(this) {
            showToast("題目已抽完")
            goBack()
        }
    }

    private fun getInitData() {
        questionViewModel.getQuestionList(cardModel.cardTopic.jsonFileName, cardModel.answerList)
    }

    private fun goBack() {
        finish()
    }

    private fun returnData(ability: Int) {
        Intent().apply {
            putExtra("ability", ability)
            putExtra("position", position)
            if (questionId != null) putExtra("question", questionId)
            setResult(RESULT_OK, this)
        }
        goBack()
    }


    private fun changeAnswerData(newPlanPosition: Int) {
        questionViewModel.apply {
            updateAnswerSelectedData()
            checkPlanDataAndRefreshItemView(getAnswerChoosePosition())
            updateAnswerSelectedData(newPlanPosition)
            checkPlanDataAndRefreshItemView(getAnswerChoosePosition())
        }
    }

    private fun checkPlanDataAndRefreshItemView(position: Int) {
        if (position in 0 until questionAdapter.currentList.size) {
            questionAdapter.notifyItemChanged(position)
        }
    }

    private fun showToast(message: String) {
        makeCenterToast(message)
    }

    private fun showAlert() {
        MaterialAlertDialogBuilder(this).setTitle(resources.getString(R.string.dialogTitle))
            .setMessage(resources.getString(R.string.dialogAnswerMessage))
            .setNegativeButton(resources.getString(R.string.dialogCancel)) { dialog, which ->
                // Respond to negative button press
                dialog.dismiss()
            }.setPositiveButton(resources.getString(R.string.dialogConfirm)) { dialog, which ->
                // Respond to positive button press
                binding.clQuestionBoardAnswer.visible(true)
                val ability = if (questionViewModel.checkAnswer()) {
                    binding.ivQuestionBoardAnswer.setImageResource(R.drawable.correct)
                    questionViewModel.getCorrectAnswer()
                } else {
                    binding.ivQuestionBoardAnswer.setImageResource(R.drawable.error)
                    -1
                }
                Handler(Looper.getMainLooper()).postDelayed({ returnData(ability) }, 2000)
            }.show()
    }
}