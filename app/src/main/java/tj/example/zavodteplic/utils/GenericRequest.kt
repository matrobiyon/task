package tj.example.zavodteplic.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import tj.example.zavodteplic.auth.data.remote.model.DetailParent
import tj.example.zavodteplic.auth.data.remote.model.DetailParentMessage
import tj.example.zavodteplic.auth.data.remote.model.RegisterUser
import java.io.IOException

suspend fun <T> callGenericRequest(
    request: suspend () -> Response<T>,
    refreshToken: suspend () -> Response<RegisterUser?>,
    sharedPref: CoreSharedPreference,
    gson: Gson
): Flow<Resource<T?>> = flow {
    emit(Resource.Loading())
    try {
        var result = request()
        if (result.isSuccessful) {
            delay(500)
            emit(Resource.Success(result.body()!!))
        } else if (result.code() == 422 || result.code() == 400) {
            emit(
                Resource.Error(
                    message = giveMeError(gson, result.errorBody()?.string() ?: "") ?: ""
                )
            )
        } else if (result.code() == 401) {
            emit(
                Resource.Error(
                    message = giveMeError(gson, result.errorBody()?.string() ?: "") ?: ""
                )
            )
            val res = refreshToken()
            if (res.isSuccessful) {
                sharedPref.setAccessToken(res.body()?.accessToken)
                sharedPref.setRefreshToken(res.body()?.refreshToken)
            }
            result = request()
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()!!))
            } else {
                emit(
                    Resource.Error(
                        message = giveMeError(
                            gson,
                            result.errorBody()?.string() ?: ""
                        ) ?: ""
                    )
                )
            }
        } else {
            emit(
                Resource.Error(
                    message = giveMeError(gson, result.errorBody()?.string() ?: "") ?: ""
                )
            )
        }
    } catch (e: IOException) {
        emit(Resource.Error(message = "IO exception..."))
    } catch (e: HttpException) {
        emit(Resource.Error(message = "Http exception..."))
    } catch (e: Exception) {
        Log.d("TAG", "callGenericRequest: $e")
        emit(Resource.Error(message = "$e..."))
    }
}

fun giveMeError(gson: Gson, errorJson: String): String? {
    Log.d("TAG", "giveMeError: $errorJson")
    if (errorJson == "") return ""
    return try {
        ((gson.fromJson(errorJson, DetailParent::class.java)) as DetailParent).detail.first().msg
    } catch (e: JsonSyntaxException) {
        try {
            ((gson.fromJson(
                errorJson,
                DetailParentMessage::class.java
            )) as DetailParentMessage).detail.message
        } catch (e: Exception) {
            ""
        }
    }
}