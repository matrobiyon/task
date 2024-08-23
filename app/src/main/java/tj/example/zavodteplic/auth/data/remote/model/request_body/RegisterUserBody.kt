package tj.example.zavodteplic.auth.data.remote.model.request_body

data class RegisterUserRequestBody(
    val phone : String,
    val name : String,
    val username : String
)