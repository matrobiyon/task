package tj.example.zavodteplic.profile.presentation.model.body

data class EditProfileBody(
    val name : String,
    val username : String,
    val birthday : String?,
    val city : String?,
    val vk : String?,
    val instagram : String?,
    val status : String?
)