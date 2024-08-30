package com.example.githubviewer

import android.content.SharedPreferences
import androidx.core.content.edit

class KeyValueStorage(
    private val sharedPreferences: SharedPreferences
) {
    var authToken: String
        get() = sharedPreferences.getString("authToken", "").orEmpty()
        set(value) = sharedPreferences.edit {
            putString("authToken", value)
        }
}