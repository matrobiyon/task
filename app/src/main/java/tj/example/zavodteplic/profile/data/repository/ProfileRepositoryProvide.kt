package tj.example.zavodteplic.profile.data.repository

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.example.zavodteplic.MainApplication
import tj.example.zavodteplic.auth.data.remote.interceptor.AuthInterceptor
import tj.example.zavodteplic.auth.di.AuthModule.TAGS
import tj.example.zavodteplic.auth.util.Utils
import tj.example.zavodteplic.profile.data.remote.model.ProfileApi
import tj.example.zavodteplic.utils.CoreSharedPreference

class ProfileRepositoryProvide {

    companion object {

        private fun getSharedPref(application: Application): CoreSharedPreference {
            return CoreSharedPreference(
                application.getSharedPreferences(
                    TAGS.CORE_SHARED_PREFERENCE,
                    Context.MODE_PRIVATE
                )
            )
        }

        fun getClient(application: Application) : OkHttpClient {
            val interceptor = AuthInterceptor(getSharedPref(application).getAccessToken() ?: "null")
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor).build()
        }

        private fun provideProfileApi(application: Application): ProfileApi {


            return Retrofit.Builder().baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(application))
                .build()
                .create(ProfileApi::class.java)

        }

        fun provideProfileRepository(application: Application): ProfileRepository =
            ProfileRepository(
                api = provideProfileApi(application),
                dao = (application as MainApplication).userDb?.userDao(),
                sharedPreference = getSharedPref(application),
            )
    }

}