package tj.example.zavodteplic.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tj.example.zavodteplic.auth.presentation.ui.AuthScreen
import tj.example.zavodteplic.auth.presentation.ui.SplashScreen
import tj.example.zavodteplic.chats.presantation.ui.ChatScreen
import tj.example.zavodteplic.chats.presantation.ui.MainPage

@Composable
fun MainNavigation(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    NavHost(navController = navController, startDestination = NavigationTags.MAIN_SCREEN) {
        composable(route = NavigationTags.SPLASH_SCREEN) {
            SplashScreen(navController)
        }

        composable(route = NavigationTags.AUTH_SCREEN) {
            AuthScreen(snackbarHostState, navController)
        }

        composable(route = NavigationTags.MAIN_SCREEN) {
            MainPage(navController)
        }

        composable(route = NavigationTags.CHAT_SCREEN){
            ChatScreen(navController)
        }
    }


}

object NavigationTags {
    const val SPLASH_SCREEN = "SplashScreen"
    const val AUTH_SCREEN = "AuthScreen"
    const val MAIN_SCREEN = "MainScreen"
    const val PROFILE_SCREEN = "Profile"
    const val CHATS_SCREEN = "Chats"
    const val CHAT_SCREEN = "Chat"
}