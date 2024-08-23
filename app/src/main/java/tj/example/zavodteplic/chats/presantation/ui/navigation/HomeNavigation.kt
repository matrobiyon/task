package tj.example.zavodteplic.chats.presantation.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tj.example.zavodteplic.chats.presantation.ui.ChatsScreen
import tj.example.zavodteplic.chats.presantation.viewModel.ChatsViewModel
import tj.example.zavodteplic.profile.presentation.ui.ProfileScreen
import tj.example.zavodteplic.utils.NavigationTags

@Composable
fun HomeNavigation(
    navController: NavHostController,
    prevNavController: NavController
) {

    NavHost(navController = navController, startDestination = NavigationTags.CHATS_SCREEN) {
        composable(route = NavigationTags.CHATS_SCREEN) {
            ChatsScreen(prevNavController)
        }

        composable(route = NavigationTags.PROFILE_SCREEN) {
            ProfileScreen()
        }
    }

}