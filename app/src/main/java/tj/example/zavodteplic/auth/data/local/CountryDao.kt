package tj.example.zavodteplic.auth.data.local

import androidx.room.Dao
import androidx.room.Query
import tj.example.zavodteplic.auth.data.local.model.Country

@Dao
interface  CountryDao {

    @Query("SELECT * FROM country WHERE code = :code")
    fun getCountries(code : String) : List<Country>

}