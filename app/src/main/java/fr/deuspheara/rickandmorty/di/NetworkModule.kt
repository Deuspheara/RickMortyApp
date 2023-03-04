package fr.deuspheara.rickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.deuspheara.rickandmorty.BuildConfig
import fr.deuspheara.rickandmorty.data.api.CharactersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    /**
     * Provides Retrofit
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides CharactersApi
     * @param retrofit: Retrofit
     * @return CharactersApi
     */
    @Singleton
    @Provides
    fun providesCharactersApi(retrofit: Retrofit) : CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

}