package tj.example.zavodteplic.auth.data.remote.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("refresh_token")
    val refreshToken : String,
    @SerializedName("access_token")
    val accessToken : String,
    @SerializedName("user_id")
    val  userId: Int,
    val detail : Detail ? = null,
    val message : String? = null
)