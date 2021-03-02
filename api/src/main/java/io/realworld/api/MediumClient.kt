package io.realworld.api

import io.realworld.api.services.MediumAPI
import io.realworld.api.services.MediumAuthAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object MediumClient {

    var token:String?=null

    private val authInterceptor= Interceptor{ chain ->
        var request=chain.request()
        token?.let{
            request= request.newBuilder()
                .header("Authorization" ,"Token $it")
                .build()
        }
        chain.proceed(request)
    }

    val okHttpBuilder= OkHttpClient.Builder()
        .readTimeout(10,TimeUnit.SECONDS)
        .connectTimeout(10,TimeUnit.SECONDS)
    val retrofit= Retrofit.Builder().
            baseUrl("https://conduit.productionready.io/api/")
            .addConverterFactory(MoshiConverterFactory.create())

    val mediumAPI= retrofit
        .client(okHttpBuilder.build())
        .build()
        .create(MediumAPI::class.java)
    val mediumAuthAPI=retrofit
        .client(okHttpBuilder.addInterceptor(authInterceptor).build())
        .build()
        .create(MediumAuthAPI::class.java)
}