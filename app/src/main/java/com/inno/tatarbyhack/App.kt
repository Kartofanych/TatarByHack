package com.inno.tatarbyhack

import android.app.Application
import com.inno.tatarbyhack.di.AppModule
import com.inno.tatarbyhack.di.AppModuleImpl

class App:Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}