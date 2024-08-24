package tj.example.zavodteplic.profile.data.remote.model

import retrofit2.Response
import retrofit2.http.GET
import tj.example.zavodteplic.profile.presentation.model.ProfileDataParent


interface ProfileApi {

    @GET("api/v1/users/me/")
    suspend fun getUser() : Response<ProfileDataParent>

}