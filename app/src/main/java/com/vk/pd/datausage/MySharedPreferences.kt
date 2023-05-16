package com.vk.pd.datausage

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences

class MySharedPreferences {


    companion object {
        val EXCLUDE_APPS_PREFERENCES = "com.drnoob.datamonitor_exclude_apps_preferences"


        fun getExcludeAppsPrefs(context: Context?): SharedPreferences? {
            var preferences: SharedPreferences? = null
            if (context != null) {
                preferences = context.getSharedPreferences(
                    EXCLUDE_APPS_PREFERENCES,
                    Context.MODE_PRIVATE
                )
            }
            return preferences
        }
    }
}


