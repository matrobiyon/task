package tj.example.zavodteplic.utils

import android.content.Context
import android.content.SharedPreferences
import tj.example.zavodteplic.auth.di.AuthModule.TAGS

class CoreSharedPreference(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(TAGS.CORE_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    fun getToken() : String? {
        return sharedPreferences.getString(TAGS.TOKEN,null)
    }

    fun setToken(token : String, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(TAGS.TOKEN,token).apply()
    }


}