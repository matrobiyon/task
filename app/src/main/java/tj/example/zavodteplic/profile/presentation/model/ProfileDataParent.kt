package tj.example.zavodteplic.profile.presentation.model

import com.google.gson.annotations.SerializedName
import tj.example.zavodteplic.profile.data.local.ProfileData

data class ProfileDataParent(
    @SerializedName("profile_data")
    val profileData: ProfileData?,
)