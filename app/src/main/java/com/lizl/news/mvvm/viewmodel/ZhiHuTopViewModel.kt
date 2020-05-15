package com.lizl.news.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.zhihu.ZhiHuAnswerModel
import com.lizl.news.model.news.zhihu.ZhiHuAuthorModel
import com.lizl.news.model.news.zhihu.top.ZhiHuAnswersResponseModel
import com.lizl.news.mvvm.repository.RepositoryManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ZhiHuTopViewModel : ViewModel()
{
    private val answerListLiveData = MutableLiveData<MutableList<ZhiHuAnswerModel>>()
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
            val questionList = mutableListOf<ZhiHuAnswerModel>()
            val zhiHuAnswersResponseModel = newsDataRepository.getNewsDetail(nextUrl)
            if (zhiHuAnswersResponseModel is ZhiHuAnswersResponseModel)
            {
                zhiHuAnswersResponseModel.dataList?.forEach {
                    it.author ?: return@forEach
                    questionList.add(ZhiHuAnswerModel(it.content, ZhiHuAuthorModel(it.author.name, it.author.avatar_url, it.author.headline)))
                }
                nextUrl = zhiHuAnswersResponseModel.paging?.next.orEmpty()
                questionTitleLiveData.postValue(zhiHuAnswersResponseModel.dataList?.first()?.question?.title)
                answerCountLiveData.postValue(zhiHuAnswersResponseModel.paging?.totals ?: questionList.size.toLong())
            }
            answerListLiveData.postValue(questionList)
        }
    }
}