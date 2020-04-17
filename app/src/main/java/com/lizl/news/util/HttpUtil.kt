package com.lizl.news.util

import android.util.Log
import okhttp3.*
import java.io.IOException
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

    fun requestData(url: String, resultCallBack: (Boolean, String) -> Unit)
    {
        Log.d(TAG, "requestData() called with: url = [$url]")
        val request: Request = Request.Builder().url(url).get().build()
        httpClient.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                val errorMessage = e.message.orEmpty()
                Log.d(TAG, "onFailure() called with: errorMessage = [$errorMessage]")
                resultCallBack.invoke(false, errorMessage)
            }

            override fun onResponse(call: Call, response: Response)
            {
                try
                {
                    val data = response.body()?.string().orEmpty()
                    Log.d(TAG, "onResponse() called with: response = [$data]")
                    resultCallBack.invoke(true, data)
                }
                catch (e: Exception)
                {
                    Log.d(TAG, "onResponse failed called with: response = [${e.message}]")
                    resultCallBack.invoke(false, e.message.orEmpty())
                }
            }
        })
    }
}