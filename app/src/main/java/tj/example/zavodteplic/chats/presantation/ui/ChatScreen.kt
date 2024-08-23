package tj.example.zavodteplic.chats.presantation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tj.example.zavodteplic.R
import tj.example.zavodteplic.chats.presantation.models.ChatMessage

@Composable
fun ChatScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                elevation = 4.dp,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape),
                    )
                    Text(
                        text = "Matrobiyon Qosim",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        },
    ) { paddingValues ->

        var smsText by remember { mutableStateOf("") }

        var messages by remember {
            mutableStateOf(getChatList())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = LazyListState(messages.size - 1),
            ) {
                items(messages.size) { message ->
                    MessageBubble(messages[message])
                }
            }

            Divider(
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = smsText,
                    onValueChange = { smsText = it },
                    shape = ShapeDefaults.Large,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "",
                            tint = Color(0xFF008000),
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .clickable {
                                    if (smsText.isNotEmpty()) {
                                        val newList = mutableListOf<ChatMessage>()
                                        newList.addAll(messages)
                                        newList.add(ChatMessage(2, smsText))
                                        messages = newList
                                    }
                                    smsText = ""
                                }
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Face, contentDescription = "")
                    }
                )
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (message.sender == 1) Arrangement.Start else Arrangement.End
    ) {
        if (message.sender == 2) {
            Card(
                elevation = CardDefaults.cardElevation(2.dp),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                )
            }
        } else {
            // Received message
            Card(
                elevation = CardDefaults.cardElevation(2.dp),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.7f)

            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_1),
                        contentDescription = "User profile",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Qosim",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = message.content,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

fun getChatList(): List<ChatMessage> {
    return listOf(
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(2, "Repeating this message"),
        ChatMessage(1, "Repeating this message"),
        ChatMessage(1, "Yes"),
        ChatMessage(2, "Do you mean for scroll displaying?"),
        ChatMessage(1, "The next sms are all should be repeated"),
        ChatMessage(2, "I'm good too. Let's talk about something else."),
        ChatMessage(1, "I'm doing great, thanks. What about you?"),
        ChatMessage(2, "Hi! How are you?"),
        ChatMessage(1, "Hello there!"),
    )
}