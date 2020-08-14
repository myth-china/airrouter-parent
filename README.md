AirRouter
======

AirRouter是一款轻量级的路由库，目前与主流Router的主要区别为：

* 不需要引入gralde插件即可使用，避免出现不同插件间的兼容问题
* 内部无反射，依赖编译时代码生成，提高运行时性能
* Service注册方式规范化，自动生成Service注册代码，AirInit_clsNameSuffix
* Service调用方式规范化，自动生成Service获取方法，AirServices_clsNameSuffix


下沉模块中定义接口
---------
下沉模块中定义接口，供无依赖关系的业务线调用

```kotlin
interface IModuleAService {

    fun getHelloWorld(): String
}
```


业务模块中定义实现
----------------

业务模块中定义实现，提供调用业务方真实的业务实现，实现类必须加上@AirService注解，标明这是一个路由服务

先在模块中导入airrouter库

```groovy
apply plugin:'kotlin-kapt'

dependencies {
  implementation 'com.github.myth-china:airrouter:1.0.0'
  kapt 'com.github.myth-china:airrouter-processor:1.0.0'
}
```

定义实现类并且用@AirService注解

```kotlin
@AirService
class ModuleAServiceImpl : IModuleAService {

    override fun getHelloWorld(): String {
        return "Hello world!"
    }
}
```

每个实现模块中需要定义一个配置类，主要用于配置自动生成的AirServices后缀名，方便调用时获取

```kotlin
@AirConfig("ModuleA")
class AppRouterConfig
```


自动生成服务注册代码
------------

代码会生成在实现模块中

```kotlin
object AirInit_ModuleA {
  fun services(): HashMap<String, Any> {
    val serviceMap = HashMap<String,Any>()
    serviceMap["com.myth.arch.modulea.ModuleAServiceImpl"] =
        com.myth.arch.modulea.IModuleAServiceImpl()
    return serviceMap
  }
}
```


手动注册需要的服务
------------

使用此服务前必须先进行注册，一般在application中进行注册

先在模块中导入airrouter库，其次要导入实现库中自动生成的服务注册代码

```groovy
dependencies {
  implementation 'com.github.myth-china:airrouter:0.0.1-SNAPSHOT'
  implementation project(':ModuleA')
}
```

对需要使用的服务手动注册

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AirRouter.registerServices(
            AirInit_ModuleA.services(),
        )
    }
}
```


自动生成服务获取代码
------------

airrouter会为每个实现模块生成对应的服务获取类，代码生成在接口模块中

```kotlin
object AirServices_ModuleA {
  fun getModuleAServiceImpl(): IModuleAService =
      getService("com.myth.arch.modulea.ModuleAServiceImpl") as
      com.myth.arch.imodulea.IModuleAService
}
```


使用已注册的服务
------------

导入服务模块

```groovy
dependencies {
    implementation project(':IModuleA')
}
```

直接通过自动生成的服务获取类获取服务

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(
            this,
            AirServices_ModuleA.getModuleAServiceImpl().getHelloWorld(),
            Toast.LENGTH_LONG
        ).show()
    }
}
```


License
-------

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.