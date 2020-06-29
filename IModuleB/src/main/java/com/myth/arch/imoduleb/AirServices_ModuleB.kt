package com.myth.arch.imoduleb

import com.myth.arch.airrouter.AirRouter.getService

object AirServices_ModuleB {
  fun getModuleBRouter(): IModuleBRouter = getService("com.myth.arch.moduleb.ModuleBRouter") as
      com.myth.arch.imoduleb.IModuleBRouter

  fun getModuleBServiceImpl3(): IModuleBService =
      getService("com.myth.arch.moduleb.ModuleBServiceImpl3") as
      com.myth.arch.imoduleb.IModuleBService

  fun getModuleBServiceImpl2(): IModuleBService =
      getService("com.myth.arch.moduleb.ModuleBServiceImpl2") as
      com.myth.arch.imoduleb.IModuleBService

  fun getModuleBServiceImpl(): IModuleBService =
      getService("com.myth.arch.moduleb.ModuleBServiceImpl") as
      com.myth.arch.imoduleb.IModuleBService

  fun getModuleBServiceImpl4(): IModuleBService =
      getService("com.myth.arch.moduleb.ModuleBServiceImpl4") as
      com.myth.arch.imoduleb.IModuleBService
}
