package com.myth.arch.airrouter

import kotlin.reflect.KClass

object AirRouter {

    const val INIT_CLS_NAME = "AirInit"
    const val INIT_FUNC_NAME = "services"

    //The class where you can get your service
    const val STORE_CLS_NAME = "AirServices"

    private val serviceMap = HashMap<String, Any>()

    private var autoGenerateServices: Boolean = true

    /**
     * Auto generate AirServices_Suffix class.
     */
    @JvmStatic
    fun enableAutoGenerateServices(enable: Boolean) {
        this.autoGenerateServices = enable
    }

    @JvmStatic
    fun isAutoGenerateServices(): Boolean {
        return this.autoGenerateServices
    }

    @JvmStatic
    fun registerService(name: String, service: Any) {
        serviceMap[name] = service
    }

    @JvmStatic
    fun <T : Any> registerService(cls: KClass<T>, service: Any) {
        val name = cls.qualifiedName ?: throw IllegalStateException("Class error")
        registerService(name, service)
    }

    @JvmStatic
    fun registerServices(vararg serviceMaps: HashMap<String, Any>) {
        serviceMaps.forEach {
            this.serviceMap.putAll(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : Any> getService(name: String): T {
        return serviceMap[name] as T
    }


    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : Any> getService(cls: KClass<T>): T {
        val name = cls.qualifiedName ?: throw IllegalStateException("Service not register error")
        return getService(name)
    }

    @JvmStatic
    fun getServices(): HashMap<String, Any> {
        return serviceMap
    }
}