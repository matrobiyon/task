package tj.example.zavodteplic.auth.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor (private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
    )
}