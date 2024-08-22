package tj.example.zavodteplic.auth.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase
import tj.example.zavodteplic.auth.data.local.model.Country

@Dao
interface  CountryDao {

    @Query("SELECT * FROM country WHERE code = :code")
    fun getCountries(code : String) : List<Country>

}