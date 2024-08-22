package tj.example.zavodteplic.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import tj.example.zavodteplic.auth.di.AuthModule.TAGS

class CoreSharedPreference(context: Context) {

    private val sharedPreferences =  context.getSharedPreferences(TAGS.CORE_SHARED_PREFERENCE, Context.MODE_PRIVATE)

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
        sharedPreferences.edit().putInt(TAGS.ACCESS_TOKEN,id).apply()
    }

}