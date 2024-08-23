package tj.example.zavodteplic.chats.presantation.models

data class Chat(
    val name: String,
    val lastMessage: String,
    val timestamp: Long,
    val profileImage: Int
)