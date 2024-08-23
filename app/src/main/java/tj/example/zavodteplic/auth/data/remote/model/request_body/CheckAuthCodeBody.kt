package tj.example.zavodteplic.auth.data.remote.model.request_body

data class CheckAuthCodeBody(
    val phone : String,
    val code : String
)