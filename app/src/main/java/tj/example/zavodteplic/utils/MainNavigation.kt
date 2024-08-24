package tj.example.zavodteplic.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tj.example.zavodteplic.auth.presentation.ui.AuthScreen
import tj.example.zavodteplic.auth.presentation.ui.SplashScreen
import tj.example.zavodteplic.chats.presantation.ui.ChatScreen
import tj.example.zavodteplic.chats.presantation.ui.MainScreen
import tj.example.zavodteplic.utils.animation.ScaleTransitionDirection
import tj.example.zavodteplic.utils.animation.scaleIntoContainer
import tj.example.zavodteplic.utils.animation.scaleOutOfContainer

@Composable
fun MainNavigation(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    NavHost(navController = navController, startDestination = NavigationTags.SPLASH_SCREEN) {
        composable(route = NavigationTags.SPLASH_SCREEN, enterTransition = {
            scaleIntoContainer()
        }, exitTransition = {
            scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
        }, popEnterTransition = {
            scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
        }, popExitTransition = {
            scaleOutOfContainer()
        }) {
            SplashScreen(navController)

        }

        composable(route = NavigationTags.AUTH_SCREEN, enterTransition = {
            scaleIntoContainer()
        }, exitTransition = {
            scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
        }, popEnterTransition = {
            scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
        }, popExitTransition = {
            scaleOutOfContainer()
        }) {
            AuthScreen(snackbarHostState, navController)
        }

        composable(route = NavigationTags.MAIN_SCREEN, enterTransition = {
            scaleIntoContainer()
        }, exitTransition = {
            scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
        }, popEnterTransition = {
            scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
        }, popExitTransition = {
            scaleOutOfContainer()
        }) {
            MainScreen(navController)
        }

        composable(route = NavigationTags.CHAT_SCREEN + "/{${NavigationTags.NAME_ARG}}/{${NavigationTags.IMAGE_ARG}}",
            enterTransition = {
                scaleIntoContainer()
            },
            exitTransition = {
                scaleOutOfContainer(direction = ScaleTransitionDirection.INWARDS)
            },
            popEnterTransition = {
                scaleIntoContainer(direction = ScaleTransitionDirection.OUTWARDS)
            },
            popExitTransition = {
                scaleOutOfContainer()
            }) {
            val name = it.arguments?.getString(NavigationTags.NAME_ARG)
            val image = it.arguments?.getString(NavigationTags.IMAGE_ARG)
            ChatScreen(navController, name, image)
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


    const val NAME_ARG = "name_arg"
    const val IMAGE_ARG = "image_arg"
}