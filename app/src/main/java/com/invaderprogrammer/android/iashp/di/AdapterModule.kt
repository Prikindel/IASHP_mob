package com.invaderprogrammer.android.iashp.di


import android.content.Context
import android.widget.ArrayAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(AppModule::class))
class AdapterModule {

    @Provides
    @Singleton
    fun provideSpinnerModule(context: Context): ArrayAdapter<String> {
        val adapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }
}