package tj.example.zavodteplic.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import tj.example.zavodteplic.R
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileCardContent
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileItems
import tj.example.zavodteplic.profile.presentation.viewModel.ProfileViewModel
import tj.example.zavodteplic.profile.presentation.viewModel.getProfileItems

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {


    LaunchedEffect(key1 = true) {
        viewModel.getUser()
    }

    val data = viewModel.profileData.data
    if (data == null && viewModel.profileData.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (data != null) {

        val list = getProfileItems(viewModel.profileData.data!!)
        val scrollState = rememberScrollState()
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
                .verticalScroll(scrollState),
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
        }
    }
}

