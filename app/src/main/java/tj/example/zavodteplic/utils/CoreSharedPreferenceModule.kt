package tj.example.zavodteplic.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.example.zavodteplic.auth.di.AuthModule.TAGS
import javax.inject.Singleton



class CoreSharedPreference(private val sharedPreferences: SharedPreferences) {

    fun getAccessToken() : String? {
        return sharedPreferences.getString(TAGS.ACCESS_TOKEN,null)
    }

    fun getRefreshToken() : String? {
        return sharedPreferences.getString(TAGS.REFRESH_TOKEN,null)
    }

    fun getUserId() : Int {
        return sharedPreferences.getInt(TAGS.USER_ID,-1)
    }

    fun setAccessToken(token : String?) {
        sharedPreferences.edit().putString(TAGS.ACCESS_TOKEN,token).apply()
    }

    fun setRefreshToken(token : String?) {
        sharedPreferences.edit().putString(TAGS.REFRESH_TOKEN,token).apply()
    }

    fun setUserId(id : Int) {
        sharedPreferences.edit().putInt(TAGS.USER_ID,id).apply()
    }

}