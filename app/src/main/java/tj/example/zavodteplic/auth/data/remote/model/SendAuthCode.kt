package tj.example.zavodteplic.auth.data.remote.model

import com.google.gson.annotations.SerializedName

data class SendAuthCode(
    @SerializedName("is_success")
    val isSuccess : Boolean? = null,
    val detail: Detail? = null
)
