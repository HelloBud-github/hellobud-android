package com.mediakita.kuis

import android.content.Context

internal class UserPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val USERNAME = "username"
        private const val EMAIL = "email"
        private const val LOGGED_IN = "loggedin"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(USERNAME, value.username)
        editor.putString(EMAIL, value.email)
        editor.putBoolean(LOGGED_IN, value.isLoggedin)
        editor.apply()
    }

    fun getUser(): UserModel {
        val model = UserModel()
        model.username = preferences.getString(USERNAME, "")
        model.email = preferences.getString(EMAIL, "")
        model.isLoggedin = preferences.getBoolean(LOGGED_IN, false)
        return model
    }
}