package com.invaderprogrammer.android.iashp.di

import com.invaderprogrammer.android.iashp.activitys.MainActivity
import com.invaderprogrammer.android.iashp.fragments.ReadingSensorFragment
import com.invaderprogrammer.android.iashp.fragments.SensorsListFragment
import com.invaderprogrammer.android.iashp.mvp.presenter.MainPresenter
import com.invaderprogrammer.android.iashp.mvp.presenter.ReadingPresenter
import com.invaderprogrammer.android.iashp.mvp.presenter.SensorsPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, RestModule::class, MvpModule::class, AdapterModule::class))
@Singleton
interface AppComponent {

    //View
    fun inject(mainActivity: MainActivity)

    fun inject(sensorsListFragment: SensorsListFragment)
    fun inject(readingSensorFragment: ReadingSensorFragment)

    //Presenter
    fun inject(presenter: SensorsPresenter)

    fun inject(presenter: MainPresenter)
    fun inject(presenter: ReadingPresenter)

}