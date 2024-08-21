package tj.example.zavodteplic.auth.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import tj.example.zavodteplic.auth.presentation.viewModel.AuthViewModel


@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val usernameRegex = remember {
        Regex("[A-Za-z0-9_-]+")
    }
    var username by remember { mutableStateOf("") }

    var isRegistered by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        Color(0xFFFF9800),
                    ), tileMode = TileMode.Mirror
                )
            )
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            Text(
                text = if (isRegistered) "WELCOME!!" else "РЕГИСТРАЦИЯ",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Card(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .padding(32.dp),
            ) {
                if (isRegistered){
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text(text = "Номер телефона") },
                        value = "",
                        onValueChange = {}
                    )
                }else {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text(text = "Номер телефона") },
                        value = "",
                        onValueChange = {}
                    )
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
                        text = AnnotatedString(
                            if (isRegistered) "У вас нет аккаунта? Регистрация" else "У вас уже есть аккаунт? Вход",
                        ),

                        ) {
                        isRegistered = !isRegistered
                    }
                }
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }


//    Box(modifier = Modifier
//        .fillMaxSize()
//        .background(color = MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
//        Card(modifier = Modifier
//            .background(MaterialTheme.colorScheme.onPrimary)
//            .padding(16.dp), shape = CardDefaults.elevatedShape, elevation = CardDefaults.cardElevation()) {
//            Text(text = if(viewModel.isRegistered) "Добро пожаловать" else "Регистрация", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//            Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center){
//                Column {
//                    TextField(value = "Номер телефона", onValueChange = {})
//                    Spacer(modifier = Modifier.size(32.dp))
//                    TextField(value = "Имю", onValueChange = {})
//                    Spacer(modifier = Modifier.size(32.dp))
//                    TextField(value = username, onValueChange = { it : String ->
//                        if (usernameRegex.matches(it)) username = it
//                    }, singleLine = true)
//                    Button(onClick = { /*TODO*/ }, modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
//                        Text(text = if(viewModel.isRegistered) "Войти" else "Зарегистрировать")
//                    }
//                }
//
//            }
//        }
//    }
}

