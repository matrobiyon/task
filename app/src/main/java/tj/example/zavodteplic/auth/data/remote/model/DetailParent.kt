package tj.example.zavodteplic.auth.data.remote.model

data class DetailParent(
    val detail: List<Detail>,
)

data class DetailParentMessage(
    val detail : Message
)

data class Message(
    val message: String
)