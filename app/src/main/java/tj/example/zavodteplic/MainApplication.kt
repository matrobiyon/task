package tj.example.zavodteplic

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp
import tj.example.zavodteplic.profile.data.local.UserDB


@HiltAndroidApp
class MainApplication : Application(),ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    var userDb : UserDB? = null

    override fun onCreate() {
        super.onCreate()
        userDb = UserDB.getDatabase(this)
    }

}