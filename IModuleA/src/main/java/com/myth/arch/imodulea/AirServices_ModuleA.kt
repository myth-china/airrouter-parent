package com.myth.arch.imodulea

import com.myth.arch.airrouter.AirRouter.getService

object AirServices_ModuleA {
  fun getModuleAServiceImpl(): IModuleAService =
      getService("com.myth.arch.modulea.ModuleAServiceImpl") as
      com.myth.arch.imodulea.IModuleAService

  fun getModuleARouter(): IModuleARouter = getService("com.myth.arch.modulea.ModuleARouter") as
      com.myth.arch.imodulea.IModuleARouter

  fun getModuleAService2Impl(): IModuleAService2 =
      getService("com.myth.arch.modulea.ModuleAService2Impl") as
      com.myth.arch.imodulea.IModuleAService2
}
