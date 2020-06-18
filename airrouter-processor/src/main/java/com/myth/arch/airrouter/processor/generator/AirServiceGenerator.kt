package com.myth.arch.airrouter.processor.generator

import com.myth.arch.airrouter.AirRouter
import com.myth.arch.airrouter.AirService
import com.myth.arch.airrouter.processor.utils.ClsUtils
import com.myth.arch.airrouter.processor.utils.FileUtils
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

class AirServiceGenerator(private val processingEnv: ProcessingEnvironment) :
    AbsGenerator(processingEnv) {

    /**
     * @param elements [AirService] elements
     */
    fun process(elements: Set<Element>, suffix: String?): Boolean {
        if (!checkKind(elements, ElementKind.CLASS)) {
            return false
        }

        return generateCode(elements, suffix)
    }


    /**
     * @param elements [AirService] elements
     */
    private fun generateCode(elements: Set<Element>, suffix: String?): Boolean {
        val group = elements.groupBy {
            val firstItf = ClsUtils.interfacesClass(processingEnv, it).firstOrNull() ?: return false
            val file = FileUtils.getJarFile(firstItf)
            file.absolutePath
        }

        group.keys
            .forEach {
                val firstElement = group[it]?.first()!!
                val firstTypeElement = firstElement as TypeElement
                val firstElementPkgName =
                    processingEnv.elementUtils.getPackageOf(firstElement).toString()
                val firstElementProjectDir = FileUtils.getPkgProjectDir(firstElementPkgName)
                val itfJarDir = it
                val itfJarProjectDir = FileUtils.getJarProjectDir(itfJarDir)
                val firstItfElement =
                    processingEnv.typeUtils.asElement(firstTypeElement.interfaces.first())
                val firstItfPkgName =
                    processingEnv.elementUtils.getPackageOf(firstItfElement).toString()
                val airStoreClsName =
                    if (suffix.isNullOrEmpty())
                        "${AirRouter.STORE_CLS_NAME}_${itfJarProjectDir}_${firstElementProjectDir}"
                    else
                        "${AirRouter.STORE_CLS_NAME}_${suffix}"

                val funSpecs = ArrayList<FunSpec>()

                group[it]
                    ?.forEach { element ->
                        val itfTypeMirror = (element as TypeElement).interfaces.first()
                        val itfElement = processingEnv.typeUtils.asElement(itfTypeMirror)
                        val itfTypeElement = itfElement as TypeElement

                        val getService = MemberName(AirRouter::class.qualifiedName!!, "getService")

                        @Suppress("DEPRECATION") val funSpec = FunSpec
                            .builder("get${element.simpleName}")
                            .addStatement(
                                "return %M(\"${getServiceName(element)}\") as ${itfTypeElement.qualifiedName}",
                                getService
                            )
                            .returns(itfTypeMirror.asTypeName())
                            .build()
                        funSpecs.add(funSpec)
                    }

                val typeSPec = TypeSpec.objectBuilder(airStoreClsName)
                    .addFunctions(funSpecs)
                    .build()

                val fileSpec = FileSpec
                    .builder(firstItfPkgName, airStoreClsName)
                    .addType(typeSPec)
                    .build()

                val dir =
                    FileUtils.replaceProjectDir(
                        itfJarProjectDir!!,
                        FileUtils.getGenerateCodeDir(processingEnv)!!
                    )

                val dest =
                    dir.substring(0, dir.indexOf("/build/")) + "/src/main/java"

                println("dest:$dest")

                fileSpec.writeTo(
                    File(
                        dest
                    )
                )
            }

        return true
    }
}