package com.oolyvi.yekun.data.network

import com.oolyvi.yekun.App.Companion.ACCESS_TOKEN
import com.oolyvi.yekun.utils.Constants.MAL_API_KEY
import okhttp3.Interceptor
import okhttp3.Response


/**
 * if user is not logged in, user uses X-MAL-CLIENT-ID, otherwise uses Authorization token
 */
class OAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = if (ACCESS_TOKEN.isNullOrEmpty()) {
            chain.request().newBuilder()
                .header("X-MAL-CLIENT-ID", MAL_API_KEY)
                .build()
        } else {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $ACCESS_TOKEN")
                .build()
        }
        return chain.proceed(request)
    }

}