package tj.example.zavodteplic.auth.data.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("refresh_token")
    val refreshToken : String,
    @SerializedName("access_token")
    val accessToken : String,
    @SerializedName("user_id")
    val userId: Int,
)

data class RegisterUserRequest(
    val phone : String,
    val name : String,
    val username : String
)