package tj.example.zavodteplic.profile.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.util.Utils
import tj.example.zavodteplic.profile.data.local.UserDB
import tj.example.zavodteplic.profile.data.remote.model.ProfileApi
import tj.example.zavodteplic.profile.data.repository.ProfileRepository
import tj.example.zavodteplic.utils.CoreSharedPreference
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Inject
    lateinit var client: OkHttpClient

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi =
        Retrofit.Builder().baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)

    @Provides
    @Singleton
    fun provideUserDb(app: Application): UserDB =
        Room.databaseBuilder(app, UserDB::class.java, "user_db").build()


    @Provides
    @Singleton
    fun provideProfileRepository(
        profileApi: ProfileApi,
        authApi: AuthApi,
        userDB: UserDB,
        sharedPreference: CoreSharedPreference,
        gson: Gson
    ): ProfileRepository =
        ProfileRepository(profileApi, authApi, userDB.userDao(), sharedPreference, gson)

}