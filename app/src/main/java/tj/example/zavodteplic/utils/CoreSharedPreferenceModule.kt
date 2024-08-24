package tj.example.zavodteplic.utils

import android.content.SharedPreferences
import tj.example.zavodteplic.auth.di.AuthModule.TAGS


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