package com.example.assessment.utils.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesManager<T>(private val context: Context, private val fileName: String) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun saveData(key: String, data: T) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(data)
        editor.putString(key, json)
        editor.apply()
    }

    fun getData(key: String, defaultValue: T): T {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<T>() {}.type)
        } else {
            defaultValue
        }
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }
}
