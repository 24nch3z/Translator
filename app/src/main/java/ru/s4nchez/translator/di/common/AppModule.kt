package ru.s4nchez.translator.di.common

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.s4nchez.sharedprefhelper.SharedPrefHelper
import javax.inject.Singleton

@Module
class AppModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun provideContext() = appContext

    @Provides
    @Singleton
    fun provideSharedPrefHelper() = SharedPrefHelper(appContext)

    @Provides
    @Singleton
    fun provideRetrofitClient(context: Context): Retrofit {
        return Retrofit.Builder()
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}