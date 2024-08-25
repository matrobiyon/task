package tj.example.zavodteplic.profile.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileCardContent
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileItems
import tj.example.zavodteplic.profile.presentation.viewModel.ProfileViewModel
import tj.example.zavodteplic.profile.presentation.viewModel.getProfileItems

@Composable
fun ProfileScreen(snackbarHostState: SnackbarHostState, prevNavController: NavController) {

    var viewModelKey by remember {
        mutableStateOf("")
    }

    val width = LocalConfiguration.current.screenWidthDp

    var offsetProfile by remember {
        mutableStateOf(0.dp)
    }
    var offsetEdit by remember {
        mutableStateOf((width * 2).dp)
    }
    val offsetForProfile by animateDpAsState(
        targetValue = offsetProfile,
        animationSpec = tween(durationMillis = 1000), label = "sms_screen"
    )

    val offsetForEdit by animateDpAsState(
        targetValue = offsetEdit,
        animationSpec = tween(durationMillis = 1000)
    )

    val scrollStateForProfile = rememberScrollState()
    val scrollStateForEdit = rememberScrollState()

    val viewModel: ProfileViewModel =
        viewModel(factory = ProfileViewModel.Factory, key = viewModelKey)

    val scopeForSnackBar = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        viewModel.errorEvent.collect {
            when (it) {
                is UIEvent.ShowSnackbar -> {
                    if (it.message != "") {
                        scopeForSnackBar.launch {
                            snackbarHostState.showSnackbar(it.message)
                        }
                    }
                }

                is UIEvent.RecreateViewModel -> {
                    viewModelKey = if (viewModelKey == "") "$viewModelKey+" else ""
                }
            }
        }
    }

    val data = viewModel.profileData.data
    Box(
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
    ) {
        if (data == null && viewModel.profileData.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (data != null && !viewModel.isEditing) {

            val list = getProfileItems(viewModel.profileData.data!!)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = offsetForProfile)
                    .verticalScroll(scrollStateForProfile),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                if (viewModel.profileData.isLoading) CircularProgressIndicator()
                AsyncImage(
                    data.avatar,
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 16.dp)
                        .clip(CircleShape),
                    placeholder = painterResource(id = R.drawable.image_2),
                    error = painterResource(id = R.drawable.image_1),
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        DrawProfileCardContent(text = data.name, title = "Имя пользователя", 3)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            colors = CardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            DrawProfileCardContent(data.username, "Ник нейм", 1)
                        }
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            colors = CardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContentColor = Color.White,
                                disabledContainerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            DrawProfileCardContent(
                                text = data.phone,
                                title = "Номер телефона",
                                type = 2
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = CardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        repeat(list.size) { index ->
                            DrawProfileItems(list[index])
                        }
                    }
                }

                Button(
                    onClick = {
                        offsetProfile = (-width).dp
                        offsetEdit = 0.dp
                        viewModel.isEditing = true
//                    prevNavController.navigate(NavigationTags.EDIT_SCREEN) {
//                        popUpTo(NavigationTags.PROFILE_SCREEN) {
//                            inclusive = true
//                        }
//                    }
                    },
                    colors = ButtonColors(
                        containerColor = Color(0xFFb30000),
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xFFcccccc)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp)
                ) {
                    Text(text = "Редактировать профиль", color = Color.White)
                }
            }
        } else if (data != null && viewModel.isEditing) {

            var name by remember { mutableStateOf(data.name) }
            var username by remember { mutableStateOf(data.username) }
            var city by remember { mutableStateOf(data.city) }
            var vk by remember { mutableStateOf(data.vk) }
            var instagram by remember { mutableStateOf(data.instagram) }
            var status by remember { mutableStateOf(data.status) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .offset(x = offsetForEdit)
                    .verticalScroll(scrollStateForEdit)
            ) {

                TextField(
                    value = "",
                    onValueChange = { name = it },
                    label = { Text(text = "Имя") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = { name = it },
                    label = { Text(text = "Ник нейм") },
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = { city = it },
                    label = { Text(text = "Город") },
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = { vk = it },
                    label = { Text(text = "Вконтакте") },
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = { instagram = it },
                    label = { Text(text = "Instagram") },
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(32.dp))
                TextField(
                    value = "",
                    onValueChange = { status = it },
                    label = { Text(text = "Обо мне") },
                    modifier = Modifier.fillMaxWidth())
                Button(
                    onClick = {
                        viewModel.isEditing = false
                        offsetProfile = 0.dp
                        offsetEdit = (width * 2).dp
                        if (name.isNotEmpty() && username.isNotEmpty()) {
                            viewModel.saveEdit(
                                name,
                                username,
                                "2024-08-24",
                                city,
                                vk,
                                instagram,
                                status
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .height(80.dp),
                    colors = ButtonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color(0xFFcccccc)
                    )
                ) {
                    Text(text = "Сохранить")
                }

            }
        }
    }

}

