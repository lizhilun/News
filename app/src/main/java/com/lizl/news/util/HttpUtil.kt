package com.lizl.news.util

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.*

object HttpUtil
{
    private val TAG = "HttpUtil"

    private val httpClient by lazy {
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //设置超时时间
            .readTimeout(5, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(5, TimeUnit.SECONDS) //设置写入超时时间
            .build()
    }

    fun <T> requestData(url: String, type: Class<T>): T?
    {
        try
        {
            val result = requestData(url) ?: return null
            return GsonUtils.fromJson(result, type)
        }
        catch (e: Exception)
        {
            Log.e(TAG, "requestData error:", e)
        }
        return null
    }

    private fun requestData(url: String): String?
    {
        Log.d(TAG, "requestData() called with: url = [$url]")
        return try
        {
            val request: Request = Request.Builder().url(url).get().build()
            val response = httpClient.newCall(request).execute()
            if (response.isSuccessful)
            {
                val body = response.body()?.string()
                Log.d(TAG, "requestData success: body = [$body]")
                body
            }
            else
            {
                Log.d(TAG, "requestData failed response not success")
                null
            }
        }
        catch (e: Exception)
        {
            Log.e(TAG, "requestData error:", e)
            null
        }
    }
}