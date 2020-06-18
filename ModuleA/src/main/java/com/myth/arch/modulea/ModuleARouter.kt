package com.myth.arch.modulea

import android.content.Context
import android.content.Intent
import com.myth.arch.airrouter.AirService
import com.myth.arch.imodulea.IModuleARouter

@AirService
class ModuleARouter : IModuleARouter {
    override fun startModuleAActivity(context: Context) {
        context.startActivity(Intent(context, ModuleAActivity::class.java))
    }
}