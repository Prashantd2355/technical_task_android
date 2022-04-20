package com.sliide.usermanagement.network
import com.sliide.usermanagement.Constants.Headers.AUTHORIZATION_KEY
import com.sliide.usermanagement.Constants.Headers.AUTHORIZATION_VALUE
import okhttp3.Interceptor
import okhttp3.Response

class OkhttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        requestBuilder.addHeader(AUTHORIZATION_KEY, AUTHORIZATION_VALUE)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}