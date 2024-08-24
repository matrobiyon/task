package tj.example.zavodteplic

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApplication : Application(),ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}