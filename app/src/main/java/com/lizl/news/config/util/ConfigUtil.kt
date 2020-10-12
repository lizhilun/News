package com.lizl.news.config.util

import com.blankj.utilcode.util.SPUtils
import com.lizl.news.config.annotation.BooleanConfig
import com.lizl.news.config.annotation.LongConfig
import com.lizl.news.config.annotation.StringConfig
import com.lizl.news.config.constant.ConfigConstant

object ConfigUtil
{
    private val defaultConfigMap = HashMap<String, Any>()

    fun initConfig()
    {
        ConfigConstant::class.java.declaredMethods.forEach { method ->
            val configKey = method.name.replace("\$annotations", "")
            method.annotations.forEach {
                when (it)
                {
                    is BooleanConfig -> defaultConfigMap[configKey] = it.defaultValue
                    is LongConfig    -> defaultConfigMap[configKey] = it.defaultValue
                    is StringConfig  -> defaultConfigMap[configKey] = it.defaultValue
                }
            }
        }
    }

    fun getBoolean(configKey: String): Boolean = SPUtils.getInstance().getBoolean(configKey, getDefault(configKey, false))

    fun getLong(configKey: String): Long = SPUtils.getInstance().getLong(configKey, getDefault(configKey, 0L))

    fun getString(configKey: String): String = SPUtils.getInstance().getString(configKey, getDefault(configKey, ""))

    fun set(configKey: String, value: Any)
    {
        when (value)
        {
            is Boolean -> SPUtils.getInstance().put(configKey, value, true)
            is String  -> SPUtils.getInstance().put(configKey, value, true)
            is Long    -> SPUtils.getInstance().put(configKey, value, true)
        }
    }

    private inline fun <reified T> getDefault(configKey: String, valueIfNotFind: T): T
    {
        val defaultValue = defaultConfigMap[configKey]
        return if (defaultValue is T) defaultValue else valueIfNotFind
    }
}