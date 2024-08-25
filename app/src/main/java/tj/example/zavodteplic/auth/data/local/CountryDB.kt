package tj.example.zavodteplic.auth.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tj.example.zavodteplic.auth.data.local.model.Country

@Database(entities = [Country::class], version = 1, exportSchema = true)
abstract class CountryDB : RoomDatabase() {

    abstract fun getCountryDao() : CountryDao

}