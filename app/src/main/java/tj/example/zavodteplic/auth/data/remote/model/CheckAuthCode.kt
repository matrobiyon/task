package tj.example.zavodteplic.auth.data.remote.model

import com.google.gson.annotations.SerializedName
import dagger.multibindings.IntoMap

data class CheckAuthCode(
    @SerializedName("refresh_token")
    val refreshToken : String,
    @SerializedName("access_token")
    val accessToken : String,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("is_user_exists")
    val isUserExists : Boolean
)