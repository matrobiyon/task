package tj.example.zavodteplic.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
object OwnSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year <= convertMillisToYear(System.currentTimeMillis()).toInt()
    }
}

fun convertMillisToYear(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}