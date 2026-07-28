package com.taiwanfrp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

object CookieStore {

    private val COOKIE =
        stringPreferencesKey("login_cookie")

    suspend fun save(
        context: Context,
        cookie: String
    ) {
        context.dataStore.edit {
            it[COOKIE] = cookie
        }
    }


    suspend fun get(
        context: Context
    ): String? {

        return context.dataStore.data
            .first()[COOKIE]
    }


    suspend fun clear(
        context: Context
    ) {
        context.dataStore.edit {
            it.remove(COOKIE)
        }
    }
}