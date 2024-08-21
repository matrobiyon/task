package tj.example.zavodteplic.auth.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import tj.example.zavodteplic.auth.presentation.ui.components.DrawCardContent
import tj.example.zavodteplic.auth.presentation.viewModel.AuthViewModel


@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel()) {

    val usernameRegex = remember {
        Regex("[A-Za-z0-9_-]+")
    }
    var username by remember { mutableStateOf("") }

    var isRegistered by remember { mutableStateOf(false) }

    val state = rememberScrollState()

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
            .padding(horizontal = 32.dp)
            .verticalScroll(state),
        verticalArrangement = Arrangement.Center,
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
            DrawCardContent(isRegistered = isRegistered) {
                // OnRegisterChanged
                isRegistered = !isRegistered
            }
        }
    }

}

