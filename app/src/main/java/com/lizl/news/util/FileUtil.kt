package com.lizl.news.util

import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PathUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

object FileUtil
{
    fun getCacheSize(): String = getDirSize(PathUtils.getExternalAppCachePath(), 1)

    fun clearAllCache(callback: () -> Unit)
    {
        GlobalScope.launch {
            FileUtils.deleteDir(PathUtils.getExternalAppCachePath())
            callback.invoke()
        }
    }

    private fun getDirSize(dirPath: String, fractionCount: Int): String = byte2FitMemorySize(FileUtils.getDirLength(dirPath), fractionCount)

    private fun byte2FitMemorySize(byteNum: Long, fractionCount: Int): String
    {
        return when
        {
            byteNum < 0          -> "0B"
            byteNum < 1024       -> String.format(Locale.getDefault(), "%.${fractionCount}fB", byteNum.toDouble())
            byteNum < 1048576    -> String.format(Locale.getDefault(), "%.${fractionCount}fKB", byteNum.toDouble() / 1024)
            byteNum < 1073741824 -> String.format(Locale.getDefault(), "%.${fractionCount}fMB", byteNum.toDouble() / 1048576)
            else                 -> String.format(Locale.getDefault(), "%.${fractionCount}fGB", byteNum.toDouble() / 1073741824)
        }
    }
}