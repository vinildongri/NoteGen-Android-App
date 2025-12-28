package com.example.notegen.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? = prefs.getString("jwt_token", null)

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }

    // ✅ Added methods for username
    fun saveUserName(name: String) {
        prefs.edit().putString("user_name", name).apply()
    }

    fun getUserName(): String? = prefs.getString("user_name", null)

    fun clearUserName() {
        prefs.edit().remove("user_name").apply()
    }

    // get email
    fun saveUserEmail(email: String){
        prefs.edit().putString("user_email", email).apply()
    }

    fun getUserEmail(): String? = prefs.getString("user_email", null)

    fun clearUserEmail() {
        prefs.edit().remove("user_email").apply()
    }

    // ✅ Clear all stored data (like clearing cookies)
    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
