package tj.example.zavodteplic.auth.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.example.zavodteplic.auth.data.local.CountryDB
import tj.example.zavodteplic.auth.data.local.model.Country
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.data.remote.interceptor.AuthInterceptor
import tj.example.zavodteplic.auth.data.repository.AuthRepository
import tj.example.zavodteplic.auth.util.Utils
import tj.example.zavodteplic.utils.CoreSharedPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule () {

    @Provides
    @Singleton
    fun getInterceptor(@ApplicationContext context: Context) : Interceptor {
        return AuthInterceptor(CoreSharedPreference(context).getAccessToken()?:"null")
    }


    @Provides
    @Singleton
    fun getClient(interceptor: Interceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient) : AuthApi = Retrofit.Builder()
        .baseUrl(Utils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(AuthApi::class.java)

    @Provides
    @Singleton
    fun providesAuthRepository(authApi: AuthApi, ) : AuthRepository {
        return AuthRepository(authApi)
    }

    object TAGS {
        const val CORE_SHARED_PREFERENCE = "core_share_pref"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_ID = "user_id"
    }
}
