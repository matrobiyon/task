package tj.example.zavodteplic.auth.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.TextFieldOffsetMapping
import tj.example.zavodteplic.auth.util.formatPhoneNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCardContent(isRegistered: Boolean, changeIsRegistered: () -> Unit) {

    val sheetState = rememberModalBottomSheetState()
    val scopeForBottomSheet = rememberCoroutineScope()

    var userPhoneNumber by remember {
        mutableStateOf("")
    }

    var showBottomSheet by remember { mutableStateOf(false) }
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
            if (showBottomSheet) {
                DrawBottomSheet(sheetState) {
                    scopeForBottomSheet.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }

            }

            var countryCode by remember {
                mutableStateOf("")
            }
            Row {
                OutlinedTextField(
                    value = countryCode,
                    onValueChange = {
                        if (it.length <= 3) countryCode = it
                    },
                    modifier = Modifier.fillMaxWidth(0.4f),
                    singleLine = true,
                    leadingIcon = {
                        Image(painter = painterResource(id = R.drawable.thumb_up),
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                showBottomSheet = !showBottomSheet
                            })
                    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = { text ->
                        TransformedText(AnnotatedString("+$text"), TextFieldOffsetMapping())
                    }

                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = userPhoneNumber,
                    onValueChange = { newValue ->
                        val formattedPhoneNumber = formatPhoneNumber(newValue)
                        userPhoneNumber = formattedPhoneNumber
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                label = { Text(text = "Логин") },
                value = "",
                onValueChange = {}
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
                Text(text = if (isRegistered) "Войти" else "Регистрация", fontSize = 18.sp)
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