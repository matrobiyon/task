package tj.example.zavodteplic.profile.data.remote.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.request_body.RefreshTokenBody
import tj.example.zavodteplic.profile.presentation.model.Avatars
import tj.example.zavodteplic.profile.presentation.model.ProfileDataParent
import tj.example.zavodteplic.profile.presentation.model.body.EditProfileBody


interface ProfileApi {

    @GET("api/v1/users/me/")
    suspend fun getUser() : Response<ProfileDataParent>

    @PUT("api/v1/users/me")
    suspend fun saveEdit(@Body body : EditProfileBody) : Response<Avatars>

    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refreshToken : RefreshTokenBody): Response<RegisterUser?>

}