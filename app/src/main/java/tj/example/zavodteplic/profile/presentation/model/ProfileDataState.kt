package tj.example.zavodteplic.profile.presentation.model

import tj.example.zavodteplic.profile.data.local.ProfileData

data class ProfileDataState(
    var data : ProfileData? = null,
    val isLoading : Boolean = false,
)