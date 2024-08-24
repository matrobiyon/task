package tj.example.zavodteplic.profile.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("user")
data class ProfileData(
    val avatar: String? = null,
    val avatars: String? = null,
    val birthday: String? = null,
    val city: String? = null,
    @SerializedName("completed_task")
    val completedTask: Int? = null,
    val created: String? = null,
    @PrimaryKey
    val id: Int,
    val instagram: String? = null,
    val last: String? = null,
    val name: String,
    val online: Boolean,
    val phone: String,
    val status: String? = null,
    val username: String,
    val vk: String? = null,
    val znakZodiaka: String? = null
)