package tj.example.zavodteplic.auth.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tj.example.zavodteplic.MainApplication
import tj.example.zavodteplic.R
import tj.example.zavodteplic.utils.NavigationTags

@Composable
fun SplashScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = R.drawable.splash_icon),
            contentDescription = "Water",
            modifier = Modifier.size(130.dp, 130.dp)
        )
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    val dao = (context.applicationContext as MainApplication).userDb?.userDao()
                    dao?.clearTable()
                }
                delay(2000)
                navHostController.navigate(NavigationTags.AUTH_SCREEN) {
                    popUpTo(NavigationTags.SPLASH_SCREEN) {
                        inclusive = true
                    }
                }
            }
        }
    }
}