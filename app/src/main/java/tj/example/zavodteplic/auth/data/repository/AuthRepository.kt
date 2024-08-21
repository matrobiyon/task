package tj.example.zavodteplic.auth.data.repository

import tj.example.zavodteplic.auth.data.remote.AuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    val authApi: AuthApi
) {


}