package com.myth.arch.imoduleb

import com.myth.arch.airrouter.AirRouter.getService

object AirServices_Sample {
  fun getModuleBServiceImpl2(): IModuleBService =
      getService("com.myth.arch.airrouter.sample.ModuleBServiceImpl2") as
      com.myth.arch.imoduleb.IModuleBService

  fun getModuleBServiceImpl(): IModuleBService =
      getService("com.myth.arch.airrouter.sample.ModuleBServiceImpl") as
      com.myth.arch.imoduleb.IModuleBService
}
