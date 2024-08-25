package tj.example.zavodteplic.profile.data.remote.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.request_body.RefreshTokenBody
import tj.example.zavodteplic.profile.presentation.model.ProfileDataParent


interface ProfileApi {

    @GET("api/v1/users/me/")
    suspend fun getUser() : Response<ProfileDataParent>

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refreshToken : RefreshTokenBody): Response<RegisterUser?>

}