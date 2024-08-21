package tj.example.zavodteplic.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import tj.example.zavodteplic.auth.data.remote.model.Response

interface AuthApi {

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refresh_token: String): Response

    @POST("api/v1/users/register/")
    suspend fun register(
        @Body phone: String,
        @Body name: String,
        @Body username: String
    ): Response

}