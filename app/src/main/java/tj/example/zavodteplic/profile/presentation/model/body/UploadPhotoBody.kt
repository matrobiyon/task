package tj.example.zavodteplic.profile.presentation.model.body

import com.google.gson.annotations.SerializedName

data class UploadPhotoBody(
    val filename : String,
    @SerializedName("base_64")
    val base64 : String
)