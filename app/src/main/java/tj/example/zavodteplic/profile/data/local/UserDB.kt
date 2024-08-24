package tj.example.zavodteplic.profile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileData::class], version = 1)
abstract class UserDB : RoomDatabase() {

    abstract fun userDao() : UserDao

}