package com.demo.touchwallet.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepository {
    suspend fun setDefaultWallet(context: Context, walletAddress: String) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_WALLET] = walletAddress
        }
    }

    suspend fun getDefaultWallet(context: Context): String? {
        return context.dataStore.data.map { preferences ->
            preferences[DEFAULT_WALLET]
        }.first()
    }

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            return instance ?: UserRepository().also {
                instance = it
            }
        }

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "user_preferences"
        )

        val DEFAULT_WALLET = stringPreferencesKey("DEFAULT_WALLET")
    }
}