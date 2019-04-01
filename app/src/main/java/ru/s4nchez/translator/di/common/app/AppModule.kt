package ru.s4nchez.translator.di.common.app

import android.content.Context
import dagger.Module
import dagger.Provides
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
}