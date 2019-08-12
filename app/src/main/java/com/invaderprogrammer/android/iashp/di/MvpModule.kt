package com.invaderprogrammer.android.iashp.di

import com.invaderprogrammer.android.iashp.mvp.presenter.MainPresenter
import com.invaderprogrammer.android.iashp.mvp.presenter.ReadingPresenter
import com.invaderprogrammer.android.iashp.mvp.presenter.SensorsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MvpModule {

    @Provides
    @Singleton
    fun provideSensorsPresenter(): SensorsPresenter = SensorsPresenter()

    @Provides
    @Singleton
    fun provideMainPresenter(): MainPresenter = MainPresenter()

    @Provides
    @Singleton
    fun provideReadingPresenter(): ReadingPresenter = ReadingPresenter()
}