package tj.example.zavodteplic.profile.presentation.model.body

import retrofit2.http.Part


data class EditProfileBody(
    val name : String,
    val username : String,
    val birthday : String?,
    val city : String?,
    val vk : String?,
    val instagram : String?,
    val status : String?,
    val avatar: UploadPhotoBody?
)