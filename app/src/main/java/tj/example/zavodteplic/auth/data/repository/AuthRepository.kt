package tj.example.zavodteplic.auth.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.SendAuthCode
import tj.example.zavodteplic.auth.data.remote.model.request_body.RegisterUserRequestBody
import tj.example.zavodteplic.auth.data.remote.model.request_body.SendAuthCodeBody
import tj.example.zavodteplic.utils.CoreSharedPreference
import tj.example.zavodteplic.utils.Resource
import tj.example.zavodteplic.utils.callGenericRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPref: CoreSharedPreference,
    private val gson: Gson
) {

    suspend fun registerUser(
        phone: String,
        username: String,
        name: String
    ): Flow<Resource<RegisterUser?>> =
        callGenericRequest(request = {
            authApi.registerUser(
                RegisterUserRequestBody(
                    phone,
                    name,
                    username
                )
            )
        }, refreshToken = { refreshToken() }, sharedPref = sharedPref, gson = gson)

    suspend fun sendAuthCode(phone: String): Flow<Resource<SendAuthCode?>> =
        callGenericRequest(
            request = { authApi.sendAuthCode(SendAuthCodeBody(phone)) },
            refreshToken = { refreshToken() }, sharedPref = sharedPref, gson = gson
        )

    private suspend fun refreshToken(): Response<RegisterUser?> {
        return authApi.refreshToken(sharedPref.getAccessToken() ?: "")
    }
}