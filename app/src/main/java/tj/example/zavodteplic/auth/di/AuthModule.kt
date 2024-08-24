package tj.example.zavodteplic.auth.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.data.remote.interceptor.AuthInterceptor
import tj.example.zavodteplic.auth.data.repository.AuthRepository
import tj.example.zavodteplic.auth.util.Utils
import tj.example.zavodteplic.utils.CoreSharedPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideCoreSharedPref(application: Application): CoreSharedPreference {
        return CoreSharedPreference(
            application.getSharedPreferences(
                TAGS.CORE_SHARED_PREFERENCE,
                Context.MODE_PRIVATE
            )
        )
    }

    @Provides
    @Singleton
    fun provideGson() : Gson = Gson()

    @Provides
    @Singleton
    fun getInterceptor(
        sharedPreference: CoreSharedPreference
    ): Interceptor {
        return AuthInterceptor(sharedPreference.getAccessToken() ?: "null")
    }


    @Provides
    @Singleton
    fun getClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi = Retrofit.Builder()
        .baseUrl(Utils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providesAuthRepository(
        authApi: AuthApi,
        sharedPreference: CoreSharedPreference,
        gson: Gson
    ): AuthRepository {
        return AuthRepository(authApi, sharedPreference, gson)
    }

    object TAGS {
        const val CORE_SHARED_PREFERENCE = "core_share_pref"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_ID = "user_id"
    }
}
