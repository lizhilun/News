package com.lizl.news.config.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.lizl.news.config.annotation.BooleanConfig
import com.lizl.news.config.annotation.LongConfig
import com.lizl.news.config.annotation.StringConfig
import com.lizl.news.config.constant.ConfigConstant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object ConfigUtil
{
    private val defaultConfigMap = HashMap<String, Any>()

    private lateinit var dataStore: DataStore<Preferences>

    fun initConfig(context: Context)
    {
        dataStore = context.createDataStore(name = "settings")

        ConfigConstant::class.java.declaredMethods.forEach { method ->
            val configKey = method.name.replace("\$annotations", "")
            method.annotations.forEach {
                when (it)
                {
                    is BooleanConfig -> defaultConfigMap[configKey] = it.defaultValue
                    is LongConfig -> defaultConfigMap[configKey] = it.defaultValue
                    is StringConfig -> defaultConfigMap[configKey] = it.defaultValue
                }
            }
        }
    }

    fun getBoolean(configKey: String): Boolean = getValue(configKey, false)

    fun getLong(configKey: String): Long = getValue(configKey, 0L)

    fun getString(configKey: String): String = getValue(configKey, "")

    fun set(configKey: String, value: Any)
    {
        when (value)
        {
            is Boolean -> saveValue(configKey, value)
            is String  -> saveValue(configKey, value)
            is Long    -> saveValue(configKey, value)
        }
    }

    private inline fun <reified T : Any> getValue(configKey: String, valueIfNotFind: T): T
    {
        return runBlocking { dataStore.data.map { preferences -> preferences[preferencesKey<T>(configKey)] ?: getDefault(configKey, valueIfNotFind) }.first() }
    }

    private inline fun <reified T : Any> saveValue(configKey: String, value: T)
    {
        runBlocking { dataStore.edit { preferences -> preferences[preferencesKey<T>(configKey)] = value } }
    }

    private inline fun <reified T> getDefault(configKey: String, valueIfNotFind: T): T
    {
        val defaultValue = defaultConfigMap[configKey]
        return if (defaultValue is T) defaultValue else valueIfNotFind
    }
}