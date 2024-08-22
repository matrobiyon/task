package tj.example.zavodteplic.auth.presentation.ui.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCardContent(isRegistered: Boolean, changeIsRegistered: () -> Unit) {

    var fullPhoneNumber: String by rememberSaveable { mutableStateOf("") }
    var username: String by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(32.dp),
    ) {
        if (isRegistered) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text(text = "Номер телефона") },
                value = "",
                onValueChange = {}
            )
        } else {
            TogiCountryCodePicker(
                modifier = Modifier
                    .fillMaxWidth(),
                onValueChange = { (code, phone), isValid ->
                    fullPhoneNumber = code + phone
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
                onValueChange = { username = it }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text(text = "Имя") },
                value = "",
                onValueChange = {}
            )

        }
        Spacer(modifier = Modifier.width(32.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(70.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = { /* Handle login */ }
            ) {
                Text(
                    text = if (isRegistered) "Войти" else "Регистрация",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            ClickableText(
                modifier = Modifier.padding(top = 8.dp),
                text = buildAnnotatedString {
                    if (isRegistered) {
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