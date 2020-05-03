package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.AuthorModel
import com.lizl.news.model.zhihu.ZhiHuAnswersResponseModel
import com.lizl.news.model.zhihu.ZhiHuQuestionModel
import com.lizl.news.mvvm.repository.RepositoryManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ZhiHuTopViewModel : ViewModel()
{
    private val answerListLiveData = MutableLiveData<MutableList<ZhiHuQuestionModel>>()
    private val questionTitleLiveData = MutableLiveData<String>()
    private val answerCountLiveData = MutableLiveData<Long>()

    fun getAnswerListLiveData() = answerListLiveData

    fun getQuestionTitleLiveData() = questionTitleLiveData

    fun getAnswerCountLiveData() = answerCountLiveData

    private lateinit var nextUrl: String

    private val newsDataRepository = RepositoryManager.getRepository(AppConstant.NEWS_SOURCE_ZHIHU_TOP)

    fun bindUrl(url: String)
    {
        nextUrl = url
        loadMoreAnswers()
    }

    fun loadMoreAnswers()
    {
        GlobalScope.launch {
            val questionList = mutableListOf<ZhiHuQuestionModel>()
            val zhiHuAnswersResponseModel = newsDataRepository.getNewsDetail(nextUrl)
            if (zhiHuAnswersResponseModel is ZhiHuAnswersResponseModel)
            {
                zhiHuAnswersResponseModel.dataList?.forEach {
                    it.author ?: return@forEach
                    questionList.add(ZhiHuQuestionModel("", it.content, AuthorModel(it.author.name, it.author.avatar_url, it.author.headline)))
                }
                nextUrl = zhiHuAnswersResponseModel.paging?.next.orEmpty()
                questionTitleLiveData.postValue(zhiHuAnswersResponseModel.dataList?.first()?.question?.title)
                answerCountLiveData.postValue(zhiHuAnswersResponseModel.paging?.totals ?: questionList.size.toLong())
            }
            answerListLiveData.postValue(questionList)
        }
    }
}