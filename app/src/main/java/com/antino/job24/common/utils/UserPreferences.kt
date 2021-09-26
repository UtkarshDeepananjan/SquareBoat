package com.antino.job24.common.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_pref")

class UserPreferences(context: Context) {

    private val applicationcontext = context.applicationContext

    val authtoken: Flow<String?>
        get() = applicationcontext.dataStore.data.cancellable().map { preferences ->
            preferences[KEy_Auth]
        }
    val userid: Flow<String?>
        get() = applicationcontext.dataStore.data.cancellable().map {  preferences ->
            preferences[User_id]
        }
    val userrole: Flow<Int?>
        get() = applicationcontext.dataStore.data.cancellable().map {  preferences ->
            preferences[User_role]
        }

    suspend fun saveAuthtoke(token: String) {
        applicationcontext.dataStore.edit { transform ->
            transform[KEy_Auth] = token
        }
    }
    suspend fun saveid(id: String) {
        applicationcontext.dataStore.edit { transform ->
            transform[User_id] = id
        }
    }
    suspend fun saverole(role: Int) {
        applicationcontext.dataStore.edit { transform ->
            transform[User_role] = role
        }
    }


    companion object {
        private val KEy_Auth = stringPreferencesKey(name = "key_auth")
        private val User_id = stringPreferencesKey(name = "key_id")
        private val User_role = intPreferencesKey(name = "key_role")
    }
}