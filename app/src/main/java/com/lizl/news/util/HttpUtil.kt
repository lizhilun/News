package com.lizl.news.util

import android.util.Log
import com.lizl.news.model.ResultItem
import okhttp3.*
import java.util.concurrent.TimeUnit

object HttpUtil
{
    private val TAG = "HttpUtil"

    private val httpClient by lazy {
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //设置超时时间
            .readTimeout(5, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(5, TimeUnit.SECONDS) //设置写入超时时间
            .build();
    }

    fun requestData(url: String): ResultItem
    {
        Log.d(TAG, "requestData() called with: url = [$url]")
        try
        {
            val request: Request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()
            return if (response.isSuccessful)
            {
                val body = response.body()?.string().orEmpty()
                Log.d(TAG, "requestData success: body = [$body]")
                ResultItem(true, body)
            }
            else
            {
                Log.d(TAG, "requestData failed response not success")
                ResultItem(false, "")
            }
        }
        catch (e: Exception)
        {
            Log.d(TAG, "requestData failed:" + e.message)
            return ResultItem(false, e.message.orEmpty())
        }
    }
}