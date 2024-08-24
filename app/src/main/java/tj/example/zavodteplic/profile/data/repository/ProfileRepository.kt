package tj.example.zavodteplic.profile.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.request_body.RefreshTokenBody
import tj.example.zavodteplic.profile.data.local.ProfileData
import tj.example.zavodteplic.profile.data.local.UserDao
import tj.example.zavodteplic.profile.data.remote.model.ProfileApi
import tj.example.zavodteplic.profile.presentation.model.ProfileDataParent
import tj.example.zavodteplic.utils.CoreSharedPreference
import tj.example.zavodteplic.utils.Resource
import tj.example.zavodteplic.utils.callGenericRequest


class ProfileRepository(
    private val api: ProfileApi,
    private val authApi: AuthApi,
    private val dao: UserDao,
    private val sharedPreference: CoreSharedPreference,
    private val gson: Gson
) {

    suspend fun getUser(): Flow<Resource<ProfileDataParent?>> =
        callGenericRequest(
            request = { api.getUser() },
            refreshToken = { refreshToken() },
            sharedPref = sharedPreference,
            gson = gson,
            dao = dao,
            emitFromCache = {
                val res = dao.getUserData()
                if (res.isNotEmpty()) {
                    ProfileDataParent(res.first())
                } else null
            }
        )

    suspend fun saveUserLocally(user: ProfileData?) {
        if (user != null) dao.saveUser(user)
    }

    private suspend fun refreshToken(): Response<RegisterUser?> =
        authApi.refreshToken(RefreshTokenBody(sharedPreference.getRefreshToken() ?: "null"))
}