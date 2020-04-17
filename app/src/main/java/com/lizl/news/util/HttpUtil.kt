package com.lizl.news.util

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpUtil
{
    private val httpClient by lazy {
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //设置超时时间
            .readTimeout(5, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(5, TimeUnit.SECONDS) //设置写入超时时间
            .build();
    }

    fun requestData(url: String, resultCallBack: (Boolean, String) -> Unit)
    {
        val request: Request = Request.Builder().url(url).get().build()
        httpClient.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException)
            {
                resultCallBack.invoke(false, e.message.orEmpty())
            }

            override fun onResponse(call: Call, response: Response)
            {
                try
                {
                    resultCallBack.invoke(true, response.body()?.string().orEmpty())
                }
                catch (e: Exception)
                {
                    resultCallBack.invoke(false, e.message.orEmpty())
                }
            }
        })
    }
}