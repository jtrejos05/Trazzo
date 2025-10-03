package com.example.myapplication.data.injection

import com.example.myapplication.data.Artista
import com.example.myapplication.data.datasource.services.ComentarioRetrofitService
import com.example.myapplication.data.datasource.services.ObraRetrofitService
import com.example.myapplication.data.datasource.services.UserRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://10.195.45.161:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesComentarioRetrofitService(retrofit: Retrofit): ComentarioRetrofitService{
        return retrofit.create(ComentarioRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesArtistaRetrofitService(retrofit: Retrofit): UserRetrofitService{
        return retrofit.create(UserRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesObraRetrofitService(retrofit: Retrofit): ObraRetrofitService{
        return retrofit.create(ObraRetrofitService::class.java)
    }
}