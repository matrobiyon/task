package tj.example.zavodteplic

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import tj.example.zavodteplic.profile.data.local.UserDB


@HiltAndroidApp
class MainApplication : Application() {

    var userDb : UserDB? = null

    override fun onCreate() {
        super.onCreate()
        userDb = UserDB.getDatabase(this)
    }

}