package tj.example.zavodteplic.auth.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import tj.example.zavodteplic.auth.data.remote.AuthApi
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import tj.example.zavodteplic.auth.data.remote.model.RegisterUserRequest
import tj.example.zavodteplic.auth.data.remote.model.SendAuthCode
import tj.example.zavodteplic.utils.Resource
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {

    fun registerUser(phone: String, username: String, name: String): Flow<Resource<RegisterUser?>> =
        flow {
            emit(Resource.Loading())
            try {
                val result = authApi.registerUser(
                    RegisterUserRequest(
                        phone = phone,
                        name = name,
                        username = username
                    )
                )
                if (result.code() in 200..300) {
//                    emit(Resource.Success(result.body()))
                } else if (result.code() == 422) {
                    emit(Resource.Error(message = result.message()))
                } else if (result.code() == 404) {
                    emit(Resource.Error(message = result.message()))
                } else {
                    emit(Resource.Error(message = result.message().toString()))
                }
            } catch (e: IOException) {
                emit(Resource.Error(message = "IO exception..."))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Http exception..."))
            } catch (e: Exception) {
                emit(Resource.Error(message = "${e}..."))
            }
        }

    fun sendAuthCode(phone: String): Flow<Resource<SendAuthCode?>> =
        flow {
            emit(Resource.Loading())
            Log.d("TAG", "sendAuthCode: here")
            try {
                val result = authApi.sendAuthCode(phone)
                if (result.code() in 200..300) {
                    emit(Resource.Success(result.body()))
                } else if (result.code() == 422) {
                    emit(Resource.Error(message = result.message()))
                } else if (result.code() == 404) {
                    emit(Resource.Error(message = result.message()))
                } else {
                    emit(Resource.Error(message = "${result.body()?.isSuccess} - success"))
                }
            } catch (e: IOException) {
                emit(Resource.Error(message = "IO exception..."))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "Http exception..."))
            } catch (e: Exception) {
                emit(Resource.Error(message = "$e..."))
            }
        }
}