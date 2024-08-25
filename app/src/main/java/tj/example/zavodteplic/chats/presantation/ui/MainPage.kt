package tj.example.zavodteplic.chats.presantation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import tj.example.zavodteplic.chats.presantation.models.NavigationBarItemMine
import tj.example.zavodteplic.chats.presantation.ui.navigation.HomeNavigation
import tj.example.zavodteplic.ui.theme.ZavodTeplicTheme
import tj.example.zavodteplic.utils.NavigationTags

@Composable
fun MainScreen(
    prevNavController: NavController,
    snackbarHostState: SnackbarHostState
) {
    ZavodTeplicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            val navController = rememberNavController()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        elevation = 4.dp,
                    ) {
                        Text(
                            text = "Test App",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp), contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                },
                bottomBar = {
                    NavigationBar {
                        getNavigationBarList().forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItemIndex == index,
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.title){
                                        popUpTo(NavigationTags.CHATS_SCREEN){
                                            inclusive = true
                                        }

                                    }
                                },
                                colors = NavigationBarItemColors(
                                    selectedIndicatorColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = Color.Black,
                                    unselectedTextColor = Color.Black,
                                    disabledIconColor = Color.Transparent,
                                    disabledTextColor = Color.Transparent
                                ),
                                label = { Text(text = item.title) },
                                icon = {
                                    Icon(
                                        imageVector = if (selectedItemIndex == index)
                                            item.selectedIcon
                                        else
                                            item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        )
                        .fillMaxSize()
                ) {
                    HomeNavigation(
                        navController = navController,
                        prevNavController = prevNavController,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }

}

@Composable
fun getNavigationBarList(): List<NavigationBarItemMine> = listOf(
    NavigationBarItemMine(
        title = NavigationTags.CHATS_SCREEN,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
    ),
    NavigationBarItemMine(
        title = NavigationTags.PROFILE_SCREEN,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )
)