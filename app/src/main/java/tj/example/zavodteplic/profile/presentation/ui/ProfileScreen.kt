package tj.example.zavodteplic.profile.presentation.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import kotlinx.coroutines.launch
import tj.example.zavodteplic.R
import tj.example.zavodteplic.auth.presentation.event.UIEvent
import tj.example.zavodteplic.profile.data.local.ProfileData
import tj.example.zavodteplic.profile.data.repository.ProfileRepositoryProvide
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileCardContent
import tj.example.zavodteplic.profile.presentation.ui.components.DrawProfileItems
import tj.example.zavodteplic.profile.presentation.viewModel.ProfileViewModel
import tj.example.zavodteplic.profile.presentation.viewModel.getProfileItems
import tj.example.zavodteplic.utils.OwnSelectableDates
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileScreen(snackbarHostState: SnackbarHostState, prevNavController: NavController) {

    var viewModelKey by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

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

    var imageLoader by remember {
        mutableStateOf<ImageLoader?>(null)
    }


    LaunchedEffect(key1 = viewModelKey) {
        imageLoader = ImageLoader(context).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(1.0)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .okHttpClient(ProfileRepositoryProvide.getClient(context.applicationContext as Application))
            .build()


    }


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

    val data = viewModel.profileDataM.data

    var showDatePicker by remember { mutableStateOf(false) }
    var birthday by remember { mutableStateOf<Long?>(null) }

    if (showDatePicker) {
        DatePickerModal(onDateSelected = {
            birthday = it
        }) {
            showDatePicker = false
        }
    }
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
        if (data == null && viewModel.profileDataM.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (data != null && !viewModel.isEditing) {

            var uri by remember { mutableStateOf<Uri?>(null) }
            val photoPicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
            ) { res ->
                uri = res
                if (uri.toString().isNotEmpty() && uri != null) {
                    val file = uriToFile(uri!!, context)
                    viewModel.saveEdit(
                        viewModel.profileDataM.data!!.copy(
                            avatar = uri.toString(),
                        ),
                        file
                    )
                }
            }

            val list = getProfileItems(viewModel.profileDataM.data!!)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = offsetForProfile)
                    .verticalScroll(scrollStateForProfile),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                if (viewModel.profileDataM.isLoading) CircularProgressIndicator()
                Box(modifier = (Modifier.clickable {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                })) {
                    imageLoader?.let {
                        AsyncImage(
                            imageLoader = it,
                            model = "https://plannerok.ru/${data.avatar}",
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp, 200.dp)
                                .padding(bottom = 16.dp)
                                .clip(CircleShape),
                            placeholder = painterResource(id = R.drawable.image_2),
                            error = painterResource(id = R.drawable.image_1),
                        )
                    }

                    if (viewModel.isEditProfileLoading) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        Box(modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.BottomEnd)
                            .drawBehind {
                                drawCircle(Color((0xFF246EE9)))
                            }) {

                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "icon add",
                                modifier = Modifier
                                    .size(28.dp),
                                tint = Color.White
                            )
                        }
                    }
                }

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

            if (viewModel.isEditProfileLoaded) {
                viewModel.isEditing = false
                offsetProfile = 0.dp
                offsetEdit = (width * 2).dp
                viewModel.isEditProfileLoaded = false
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .offset(x = offsetForEdit)
                    .verticalScroll(scrollStateForEdit)
            ) {
                Card {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Row(modifier = Modifier.clickable {
                            showDatePicker = true
                        }) {
                            Text(text = "Ваш год рождения:",color = Color.Black, modifier = Modifier.padding(end = 8.dp))
                            if (birthday != null)
                                Text(text = convertMillisToDate(birthday!!),color = Color.Gray)
                            else if (data.birthday != null)
                                Text(text = data.birthday,color = Color.Gray)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_edit),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Имя") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = username,
                        onValueChange = { username = it.trim() },
                        label = { Text(text = "Ник нейм") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = city ?: "",
                        onValueChange = { city = it },
                        label = { Text(text = "Город") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = vk ?: "",
                        onValueChange = { vk = it.trim() },
                        label = { Text(text = "Вконтакте") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = instagram ?: "",
                        onValueChange = { instagram = it.trim() },
                        label = { Text(text = "Instagram") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    TextField(
                        value = status ?: "",
                        onValueChange = { status = it },
                        label = { Text(text = "Обо мне") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (viewModel.isEditProfileLoading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    } else {
                        Row {
                            Button(
                                onClick = {
                                    if (name.trim().isNotEmpty() && username.trim().isNotEmpty()) {
                                        viewModel.saveEdit(
                                            ProfileData(
                                                data.avatar,
                                                birthday = if (birthday != null) formatDate(birthday!!) else data.birthday,
                                                city = if (city == "") null else city,
                                                completedTask = data.completedTask,
                                                created = data.created,
                                                id = data.id,
                                                instagram = if (instagram == "") null else instagram,
                                                last = data.last,
                                                name = name,
                                                online = data.online,
                                                phone = data.phone,
                                                status = if (status?.trim() == "") null else status,
                                                username = username,
                                                vk = if (vk == "") null else vk,
                                            )
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .height(40.dp)
                                    .weight(1f),
                                colors = ButtonColors(
                                    containerColor = Color.Green,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color(0xFFcccccc)
                                )
                            ) {
                                Text(text = "Сохранить")
                            }
                            Button(
                                onClick = {
                                    viewModel.isEditing = false
                                    offsetProfile = 0.dp
                                    offsetEdit = (width * 2).dp
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                                    .height(40.dp),
                                colors = ButtonColors(
                                    containerColor = Color.Gray,
                                    contentColor = Color.White,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = Color(0xFFcccccc)
                                )
                            ) {
                                Text(text = "Отменить")
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}

fun uriToFile(uri: Uri, context: Context): File? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val file = File(context.cacheDir, "temp_image_file")
    file.outputStream().use {
        inputStream.copyTo(it)
    }
    return file
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = OwnSelectableDates)

    DatePickerDialog(

        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun formatDate(timestamp: Long, pattern: String = "yyyy-MM-dd"): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}
