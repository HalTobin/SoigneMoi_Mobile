package com.example.soignemoi.resource

import com.example.soignemoi.data.api.TokenResponse

object AuthPlaceholder {

    val tokenResponsePlaceholder = TokenResponse("ACCESS_TOKEN", "Bearer:", "Doctor", 1L)

    val TOKEN_RESPONSE_JSON = "{" +
            "\"accessToken\": \"${tokenResponsePlaceholder.accessToken}\"," +
            "\"tokenType\": \"${tokenResponsePlaceholder.tokenType}\"," +
            "\"role\": \"${tokenResponsePlaceholder.role}\"," +
            "\"validity\": ${tokenResponsePlaceholder.validity}" +
        "}"

}