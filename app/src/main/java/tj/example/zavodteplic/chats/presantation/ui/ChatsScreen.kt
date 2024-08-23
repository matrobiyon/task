package tj.example.zavodteplic.chats.presantation.ui

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tj.example.zavodteplic.R
import tj.example.zavodteplic.chats.presantation.models.Chat
import tj.example.zavodteplic.utils.NavigationTags
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ChatsScreen(prevNavController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val list by remember {
            mutableStateOf(getTestChat())
        }

        LazyColumn {
            items(list.size) {
                ChatItem(chat = getTestChat()[it]) {
                    prevNavController.navigate(NavigationTags.CHAT_SCREEN)
                }
            }
        }
    }
}

@Composable
fun ChatItem(chat: Chat, navigateToChat: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                navigateToChat()
            },
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            painter = painterResource(id = chat.profileImage),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = chat.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = chat.lastMessage,
                style = MaterialTheme.typography.bodySmall
            )

        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            val time = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.ofEpochSecond(chat.timestamp, 0, ZoneOffset.UTC).format(
                    DateTimeFormatter.ofPattern("HH:mm")
                )
            } else {
                "20:43"
            }
            Text(
                text = time, style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

fun getTestChat(): List<Chat> {
    val list = mutableListOf<Chat>()
    repeat(10) {
        list.add(
            Chat("Qosim", "This is last message", System.currentTimeMillis(), R.drawable.image_1)
        )
        list.add(
            Chat(
                "Matrobiyon",
                "How are u doing?",
                System.currentTimeMillis() - 60 * 60,
                R.drawable.image_2
            )
        )
        list.add(
            Chat(
                "Saodatsho",
                "I am going to visit u tomorrow",
                System.currentTimeMillis() - 60 * 60 * 60,
                R.drawable.images_3
            )
        )
    }
    return list
}