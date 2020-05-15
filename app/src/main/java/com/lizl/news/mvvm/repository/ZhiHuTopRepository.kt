package com.lizl.news.mvvm.repository

import android.util.Log
import com.lizl.news.constant.AppConstant
import com.lizl.news.model.news.NewsModel
import com.lizl.news.model.news.zhihu.ZhiHuAnswersResponseModel
import com.lizl.news.model.news.zhihutop.ZhiHuTopResponseModel
import com.lizl.news.util.HttpUtil

class ZhiHuTopRepository : NewsDataRepository
{
    private val TAG = "ZhiHuTopRepository"

    override fun getLatestNews(): MutableList<NewsModel>
    {
        Log.d(TAG, "getLatestZhiHuTopNews() called")
        val requestUrl = "https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true"
        val newsList = mutableListOf<NewsModel>()

        val zhiHuTopResponseModel = HttpUtil.requestData(requestUrl, ZhiHuTopResponseModel::class.java)
        zhiHuTopResponseModel?.dataList?.forEach {
            it.target ?: return@forEach
            newsList.add(NewsModel("https://www.zhihu.com/question/${it.target.id}", it.target.title, listOf(it.children?.first()?.thumbnail.orEmpty()),
                    AppConstant.NEWS_SOURCE_ZHIHU_TOP))
        }

        return newsList
    }

    override fun loadMoreNews(): MutableList<NewsModel>
    {
        return mutableListOf()
    }

    override fun getNewsDetail(diaryUrl: String): ZhiHuAnswersResponseModel?
    {
        Log.d(TAG, "getZhiHuTopNewsDetail() called with: diaryUrl = [$diaryUrl]")

        val requestUrl = if(diaryUrl.contains("answers")) diaryUrl
        else "https://www.zhihu.com/api/v4/questions/${diaryUrl.split("/").last()}/answers?include=data%5B*%5D.is_normal%2Cadmin_closed_comment" +
             "%2Creward_info%2Cis_collapsed%2Cannotation_action%2Cannotation_detail%2Ccollapse_reason%2Cis_sticky%2Ccollapsed_by" +
             "%2Csuggest_edit%2Ccomment_count%2Ccan_comment%2Ccontent%2Ceditable_content%2Cvoteup_count%2Creshipment_settings%2Ccomment_permission" +
             "%2Ccreated_time%2Cupdated_time%2Creview_info%2Crelevant_info%2Cquestion%2Cexcerpt%2Crelationship.is_authorized%2Cis_author%2Cvoting%2Cis_thanked" +
             "%2Cis_nothelp%2Cis_labeled%2Cis_recognized%2Cpaid_info%2Cpaid_info_content%3Bdata%5B*%5D.mark_infos" +
             "%5B*%5D.url%3Bdata%5B*%5D.author.follower_count%2Cbadge%5B*%5D.topics&offset=&limit=3&sort_by=default&platform=desktop"

        return HttpUtil.requestData(requestUrl, ZhiHuAnswersResponseModel::class.java)
    }

    override fun canLoadMore() = false
}