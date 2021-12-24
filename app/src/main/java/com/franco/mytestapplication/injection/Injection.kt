package com.franco.mytestapplication.injection

import android.content.Context
import com.franco.mytestapplication.BuildConfig.BASE_URL
import com.franco.mytestapplication.common.ResourceManager
import com.franco.mytestapplication.data.PokemonRepository
import com.franco.mytestapplication.data.PokemonRepositoryImpl
import com.franco.mytestapplication.data.remote.PokemonApi
import com.franco.mytestapplication.presentation.ResourceManagerImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Contains helper classes to create and provide dependencies similar to all stuff that dagger 2
 * generate when creates its dependencies graph.
 */


object Injector {

    private var repository: PokemonRepository? = null

    var resourceManager: ResourceManager? = null

    fun injectRepository(context: Context): PokemonRepository {
        resourceManager = Provider.provideResourceManager(context)
        val okHttpClient = Provider.provideOkHttpClient()
        val retrofit = Provider.provideRetrofit(BASE_URL, okHttpClient)
        val api = Provider.provideApi(retrofit)
        return PokemonRepositoryImpl(api, resourceManager!!)
    }

    fun injectViewModelFactory(context: Context): ViewModelFactory {
        // We need only one instance
        if (repository == null) {
            repository = injectRepository(context)
        }

        if (resourceManager == null) {
            resourceManager = Provider.provideResourceManager(context)
        }

        return ViewModelFactory(
            repository!!,
            resourceManager!!
        )
    }

}

object Provider {

    var resourceManager: ResourceManager? = null
    private var httpClient: OkHttpClient? = null
    private var api: PokemonApi? = null

    fun provideResourceManager(context: Context): ResourceManager {
        if(resourceManager == null) {
            resourceManager = ResourceManagerImpl(context)
        }

        return resourceManager!!
    }

    fun provideOkHttpClient(): OkHttpClient {
        if (httpClient == null) {
            httpClient =
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()
        }

        return httpClient!!
    }

    fun provideRetrofit(
        baseUrl: String,
        httpClient: OkHttpClient
    ) =
        Retrofit
            .Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()


    fun provideApi(retrofit: Retrofit): PokemonApi {
        if (api == null) {
            api = retrofit.create(PokemonApi::class.java)
        }
        return api!!
    }
}