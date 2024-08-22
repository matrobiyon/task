package tj.example.zavodteplic.auth.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(
    val country : String,
    val code : String,
    val iso : String,
    @PrimaryKey
    val id : Int? = null
)