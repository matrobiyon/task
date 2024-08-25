package tj.example.zavodteplic.profile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile


@Database(entities = [ProfileData::class], version = 1)
abstract class UserDB : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB? {
            if (INSTANCE == null) {
                synchronized(UserDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            UserDB::class.java, "user-db"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }


}