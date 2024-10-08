package tj.example.zavodteplic.auth.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import tj.example.zavodteplic.auth.data.remote.model.CheckAuthCode
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.SendAuthCode
import tj.example.zavodteplic.auth.data.remote.model.request_body.CheckAuthCodeBody
import tj.example.zavodteplic.auth.data.remote.model.request_body.RefreshTokenBody
import tj.example.zavodteplic.auth.data.remote.model.request_body.RegisterUserRequestBody
import tj.example.zavodteplic.auth.data.remote.model.request_body.SendAuthCodeBody

interface AuthApi {

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refreshToken : RefreshTokenBody): Response<RegisterUser?>

    @POST("api/v1/users/register/")
    suspend fun registerUser(
        @Body body: RegisterUserRequestBody
    ): Response<RegisterUser>

    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body phone: SendAuthCodeBody): Response<SendAuthCode?>

    @POST("api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body checkAuthCodeBody: CheckAuthCodeBody) : Response<CheckAuthCode>

}