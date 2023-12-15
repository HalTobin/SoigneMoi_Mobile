package com.example.soignemoi.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (original.url.encodedPath.contains("/login")) {
            return chain.proceed(original)
        }

        val token = sessionManager.token

        val requestBuilder = original.newBuilder()
            .addHeader("Authorization", "Bearer $token")

        val request = requestBuilder.build()
        val response = chain.proceed(request)

        if (response.code == 401) {
            // Regenerate token here
            var newToken = ""
            runBlocking {
                newToken = sessionManager.autoConnect()
            }

            // Update the token
            sessionManager.token = newToken

            // Recreate the request with the new token and proceed again
            val newRequest = original.newBuilder()
                .addHeader("Authorization", "Bearer $newToken")
                //.url(NetworkModule.BASE_URL + "auth/login")
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }
}