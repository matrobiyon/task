package tj.example.zavodteplic.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tj.example.zavodteplic.auth.presentation.ui.AuthScreen
import tj.example.zavodteplic.auth.presentation.ui.SplashScreen

@Composable
fun MainNavigation(navController : NavHostController) {

    NavHost(navController = navController, startDestination = NavigationTags.SPLASH_SCREEN){
        composable(route = NavigationTags.SPLASH_SCREEN){
            SplashScreen(navController)
        }

        composable(route = NavigationTags.AUTH_SCREEN){
            AuthScreen()
        }
    }


}
object NavigationTags {
    const val SPLASH_SCREEN = "SplashScreen"
    const val AUTH_SCREEN = "AuthScreen"
}