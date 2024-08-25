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
    gson: Gson,
    emitFromCache: (suspend () -> T?)? = null,
): Flow<Resource<T?>> = flow {
    emit(Resource.Loading())

    val data = emitFromCache?.let {
        it()
    }

    if (data != null) emit(Resource.Success(data))


    try {
        var result = request()
        Log.d("TAG", "callGenericRequest:  ${result.code()}")
        Log.d("TAG", "callGenericRequest:  ${result.errorBody()?.string()}")
        Log.d("TAG", "callGenericRequest: ${sharedPref.getAccessToken()}")
        Log.d("TAG", "callGenericRequest: ${sharedPref.getRefreshToken()}")

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
                emit(Resource.Error(recreateViewModel = true))
                emit(Resource.Error(recreateViewModel = false))
            }

            Log.d("TAG", "callGenericRequest: refresh ${res.code()}")
            Log.d("TAG", "callGenericRequest: refresh ${res.errorBody()?.string()}")

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