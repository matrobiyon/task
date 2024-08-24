package tj.example.zavodteplic.auth.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.togitech.ccp.component.TogiCountryCodePicker
import tj.example.zavodteplic.R
import java.util.Locale


@Composable
fun DrawCardContent(
    wantToLoggIn: Boolean,
    isRegisterLoading: Boolean,
    isLoggingLoading: Boolean,
    changeIsRegistered: () -> Unit,
    showSnackbar: (message: String) -> Unit,
    registerUser: (phone: String, userName: String, name: String) -> Unit,
    sendAuthCode: (phone: String) -> Unit
) {

    val usernameRegex = remember { Regex("^[A-Za-z0-9_-]{6,}") }
    var username: String by rememberSaveable { mutableStateOf("") }
    var isLoginError by rememberSaveable { mutableStateOf(false) }

    var name: String by rememberSaveable { mutableStateOf("") }

    var fullPhoneNumber: String by rememberSaveable { mutableStateOf("") }
    var isPhoneNumberError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(32.dp),
    ) {
        if (wantToLoggIn) {
            TogiCountryCodePicker(
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = { (code, phone), isValid ->
                    fullPhoneNumber = code + phone
                    isPhoneNumberError = !isValid
                },
                label = { Text("Phone Number") },
                showCountryCode = true,
                showPlaceholder = true,
                initialCountryIsoCode = Locale.getDefault().country,
                shape = RectangleShape,
            )
        } else {
            TogiCountryCodePicker(
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = { (code, phone), isValid ->
                    fullPhoneNumber = code + phone
                    isPhoneNumberError = !isValid
                },
                label = { Text("Phone Number") },
                showCountryCode = true,
                showPlaceholder = true,
                initialCountryIsoCode = Locale.getDefault().country,
                shape = RectangleShape,
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text(text = "Логин") },
                value = username,
                onValueChange = {
                    username = it
                    isLoginError = !usernameRegex.matches(it)
                },
                isError = isLoginError,
                supportingText = {
                    if (isLoginError) {
                        Text(
                            text = "o\tЗаглавные латинские буквы: от A до Z (26 символов)\n" +
                                    "o\tСтрочные латинские буквы: от a до z (26 символов)\n" +
                                    "o\tЦифры от 0 до 9 (10 символов)\n" +
                                    "o\tСимволы: -_ (2 символа)\n" +
                                    "o\tДлина: > 5 \n",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text(text = "Имя") },
                value = name,
                onValueChange = {
                    name = it
                }
            )

        }


        Spacer(modifier = Modifier.width(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isLoggingLoading || isRegisterLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(70.dp)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        if ((isPhoneNumberError || isLoginError || name.isEmpty()) && !wantToLoggIn) {
                            showSnackbar("Заполните все поля")
                        } else if (wantToLoggIn) {
                            Log.d("TAG", "DrawCardContent: login")
                            sendAuthCode(fullPhoneNumber)
                        } else {
                            Log.d("TAG", "DrawCardContent: else")
                            registerUser(
                                fullPhoneNumber,
                                username,
                                name
                            )
                        }
                    }
                ) {
                    if (isRegisterLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                    } else {
                        Text(
                            text = if (wantToLoggIn) "Войти" else "Регистрация",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                modifier = Modifier.padding(top = 8.dp),
                text = buildAnnotatedString {
                    if (wantToLoggIn) {
                        append("У вас нет аккаунта? ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Регистрация")
                        }
                    } else {
                        append("У вас уже есть аккаунт? ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("Вход")
                        }
                    }
                })
            {
                changeIsRegistered()
            }
        }
        Spacer(modifier = Modifier.width(32.dp))
    }
}

