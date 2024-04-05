package com.example.assessment.utils.session

import android.app.Application
import com.example.assessment.feature.register.dto.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SessionManager(application: Application, name: String = PrefConstant.SESSION) {
    private var sessionName = PrefConstant.SESSION
    private var database: SharedPreferencesManager<String> =
        SharedPreferencesManager(application, name)

    companion object {
        @Volatile
        private var INSTANCE: SessionManager? = null

        fun init(application: Application, name: String) {
            INSTANCE = SessionManager(application, name)
        }

        fun getInstance(): SessionManager {
            return INSTANCE
                ?: throw IllegalStateException("SessionManager not initialized. Call init() first.")
        }
    }

    fun saveSession(user: User): Boolean {
        return try {
            val gson = Gson()
            val json = gson.toJson(user)
            database.saveData(sessionName, json)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun deleteSession(): Boolean {
        return try {
            database.clear()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun getSession(): User? {
        return try {
            val gson = Gson()
            val data = database.getData(sessionName, "")
            gson.fromJson(data, object : TypeToken<User>() {}.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    fun updateSession(user: User): Boolean {
        return try {
            val gson = Gson()
            val json = gson.toJson(user)
            database.remove(sessionName)
            database.saveData(sessionName, json)
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }


}