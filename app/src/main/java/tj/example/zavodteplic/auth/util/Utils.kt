package tj.example.zavodteplic.auth.util

object Utils {

    const val BASE_URL = "https://plannerok.ru/"

}

fun formatPhoneNumber(phoneNumber: String): String {
    // Remove all non-digit characters
    val digits = phoneNumber.filter { it.isDigit() }

    return buildString {
        for (i in digits.indices) {
            when (i) {
                0 -> append('(')
                3 -> append(") ")
                6 -> append('-')
            }
            append(digits[i])
            if (i == 9) break
        }
    }
}