package com.myth.arch.airrouter.sample

import android.app.Application
import com.myth.arch.airrouter.AirRouter
import com.myth.arch.modulea.AirInit_ModuleA
import com.myth.arch.moduleb.AirInit_ModuleB

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AirRouter.registerServices(
            AirInit_ModuleA.services(),
            AirInit_ModuleB.services()
        )
    }
}