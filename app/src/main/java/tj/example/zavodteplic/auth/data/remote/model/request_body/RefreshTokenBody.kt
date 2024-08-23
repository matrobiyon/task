package tj.example.zavodteplic.auth.data.remote.model.request_body

import com.google.gson.annotations.SerializedName

data class RefreshTokenBody(
    @SerializedName("refresh_token")
    val refreshToken : String
)