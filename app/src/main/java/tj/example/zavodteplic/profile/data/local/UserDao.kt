package tj.example.zavodteplic.profile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getUserData() : List<ProfileData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user : ProfileData)

}