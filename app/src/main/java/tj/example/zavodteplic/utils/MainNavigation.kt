package tj.example.zavodteplic.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tj.example.zavodteplic.auth.presentation.ui.AuthScreen
import tj.example.zavodteplic.auth.presentation.ui.SplashScreen
import tj.example.zavodteplic.chats.presantation.ui.ChatsScreen

@Composable
fun MainNavigation(navController : NavHostController,snackbarHostState: SnackbarHostState) {

    NavHost(navController = navController, startDestination = NavigationTags.SPLASH_SCREEN){
        composable(route = NavigationTags.SPLASH_SCREEN){
            SplashScreen(navController)
        }

        composable(route = NavigationTags.AUTH_SCREEN){
            AuthScreen(snackbarHostState,navController)
        }

        composable(route = NavigationTags.CHATS_SCREEN){
            ChatsScreen()
        }
    }


}
object NavigationTags {
    const val SPLASH_SCREEN = "SplashScreen"
    const val AUTH_SCREEN = "AuthScreen"
    const val CHATS_SCREEN = "ChatsScreen"
}