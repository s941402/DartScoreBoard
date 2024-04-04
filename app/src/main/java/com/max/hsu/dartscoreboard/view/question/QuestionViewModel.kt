package com.max.hsu.dartscoreboard.view.question

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.max.hsu.dartscoreboard.base.BaseAndroidViewModel
import com.max.hsu.dartscoreboard.model.QuestionModel
import com.max.hsu.dartscoreboard.model.TopicQuestionModel
import com.max.hsu.dartscoreboard.toolUtil.fromJsonExtend
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class QuestionViewModel(application: Application) : BaseAndroidViewModel(application) {

    // 更新方案選擇
    private var _topicQuestionResult = MutableLiveData<TopicQuestionModel>()
    val topicQuestionResult: LiveData<TopicQuestionModel> = _topicQuestionResult

    private var _questionResult = MutableLiveData<MutableList<QuestionModel>>()
    val questionResult: LiveData<MutableList<QuestionModel>> = _questionResult
    private var answerChoosePosition = -1

    private var _errorResult = MutableLiveData<Boolean>()
    val errorResult: LiveData<Boolean> = _errorResult

    fun getQuestionList(jsonFileName: String, answerList: MutableSet<Int> = mutableSetOf()) {
        val dataStr = getResList(jsonFileName)
        val topicQuestionItems =
            Gson().fromJsonExtend<MutableList<TopicQuestionModel>>(dataStr).filter {
                !answerList.contains(it.id)
            }
        if (topicQuestionItems.isEmpty()) {
            _errorResult.value = true
        } else {
            val topicQuestionItem = topicQuestionItems.random()
            val questionItems =
                topicQuestionItem.selectQuestion.map { QuestionModel(it) }.toMutableList()
            _topicQuestionResult.value = topicQuestionItem
            _questionResult.value = questionItems
        }
    }

    // 取得次數方案篩選位置
    fun getAnswerChoosePosition() = answerChoosePosition

    fun getCorrectAnswer(): Int {
        val topicQuestionResult = _topicQuestionResult.value
        return topicQuestionResult?.ability ?: -1
    }

    // 更新次數方案之前被選擇的改為false
    fun updateAnswerSelectedData() {
        val answerPickerList = _questionResult.value ?: mutableListOf()
        if (answerChoosePosition in 0 until answerPickerList.size) {
            answerPickerList.getOrNull(answerChoosePosition)?.isSelected = false
        }
    }

    // 更新次數方案新選擇的改為true並記錄新的位置
    fun updateAnswerSelectedData(currPosition: Int) {
        val answerPickerList = _questionResult.value ?: mutableListOf()
        if (currPosition in 0 until answerPickerList.size) {
            answerPickerList.getOrNull(currPosition)?.apply {
                isSelected = true
                answerChoosePosition = currPosition
            }
        }
    }

    fun hasSelectedAnswer(): Boolean {
        val answerPickerList = _questionResult.value ?: mutableListOf()
        return answerChoosePosition in 0 until answerPickerList.size
    }

    fun checkAnswer(): Boolean {
        val topicQuestionResult = _topicQuestionResult.value
        val answerPickerList = _questionResult.value ?: mutableListOf()
        return if (answerChoosePosition in 0 until answerPickerList.size) {
            topicQuestionResult?.answer == answerChoosePosition
        } else {
            false
        }
    }

    private fun getResList(jsonFileName: String?): String {
        val sb = StringBuilder()
        val assets: AssetManager = resources.assets
        var inputStream: InputStream? = null
        if (!jsonFileName.isNullOrEmpty())
            try {
                inputStream = assets.open(jsonFileName)
                val br = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                br.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
        return sb.toString()
    }
}