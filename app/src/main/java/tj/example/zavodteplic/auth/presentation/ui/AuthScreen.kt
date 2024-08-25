package tj.example.zavodteplic.auth.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.auth.presentation.ui.components.DrawCardContent
import tj.example.zavodteplic.auth.presentation.viewModel.AuthViewModel
import tj.example.zavodteplic.utils.NavigationTags


@Composable
fun AuthScreen(
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var isRegistered by remember { mutableStateOf(false) }

    val state = rememberScrollState()
    val scopeForSnackBar = rememberCoroutineScope()


    LaunchedEffect(key1 = true) {
        viewModel.errorEvent.collect {
            when (it) {
                is UIEvent.ShowSnackbar -> {
                    scopeForSnackBar.launch {
                        snackbarHostState.showSnackbar(it.message)
                    }
                }

                else -> {
                    //Recreate View Model is not neccessary here
                }
            }
        }
    }

    val width = LocalConfiguration.current.screenWidthDp

    var offsetAuth by remember {
        mutableStateOf(0.dp)
    }
    var offsetSms by remember {
        mutableStateOf((-width).dp)
    }

    val offsetForSmsCode by animateDpAsState(
        targetValue = offsetSms,
        animationSpec = tween(durationMillis = 1000), label = "sms_screen"
    )

    val offsetForAuth by animateDpAsState(
        targetValue = offsetAuth,
        animationSpec = tween(durationMillis = 1000)
    )

    if (viewModel.isRegisterLoaded || viewModel.isLoggedSuccess) {
        offsetSms = 0.dp
        offsetAuth = width.dp
    }

    if (viewModel.checkAuthCodeData?.isUserExists == true) {
        navHostController.navigate(NavigationTags.MAIN_SCREEN) {
            popUpTo(NavigationTags.MAIN_SCREEN) {
                inclusive = true
            }
        }
    } else if (viewModel.checkAuthCodeData?.isUserExists == false) {
        isRegistered = false
    }

    var fullPhoneNumber by remember { mutableStateOf("") }

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
            .verticalScroll(state)
            .offset(x = offsetForAuth),
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

            DrawCardContent(wantToLoggIn = isRegistered,
                isLoggingLoading = viewModel.isLoggingLoading,
                isRegisterLoading = viewModel.isRegisterLoading,
                changeIsRegistered = {
                    isRegistered = !isRegistered
                },
                showSnackbar = { message ->
                    viewModel.showSnackbar(message)
                },
                registerUser = { phone, userName, name ->
                    viewModel.registerUser(phone = phone, username = userName, name = name)
                    fullPhoneNumber = phone
                },
                sendAuthCode = {
                    viewModel.sendAuthCode(it)
                    fullPhoneNumber = it
                }
            )
        }
    }

    var smsCodeEntered by remember {
        mutableStateOf("")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .offset(x = offsetForSmsCode)
    ) {
        Card(
            modifier = Modifier.fillMaxHeight(0.4f),
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "previous page",
                    modifier = Modifier.clickable {
                        offsetSms = (-width).dp
                        offsetAuth = 0.dp
                        viewModel.resetDatas()
                    }
                )
                Text(
                    text = "Введите код из смса пожалуйста",
                    color = Color.Black,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
                if (viewModel.isCheckAuthLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp), contentAlignment = Alignment.Center) {
                        OutlinedTextField(
                            value = smsCodeEntered,
                            onValueChange = {
                                if (it.length <= 6) smsCodeEntered = it
                                if (it.length == 6) viewModel.checkAuthCode(fullPhoneNumber, it)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            }
        }
    }
}

