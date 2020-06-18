package com.myth.arch.airrouter.processor.generator

import com.myth.arch.airrouter.AirRouter
import com.myth.arch.airrouter.AirService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

class AirInitGenerator(private val processingEnv: ProcessingEnvironment) :
    AbsGenerator(processingEnv) {

    fun process(elements: MutableSet<out Element>, suffix: String?): Boolean {
        if (!checkKind(elements, ElementKind.CLASS)) {
            return false
        }

        return generateCode(elements, suffix)
    }

    /**
     * @param elements [AirService] elements
     */
    private fun generateCode(elements: MutableSet<out Element>, suffix: String?): Boolean {

        val propertyName = "serviceMap"

        val codeBlock = CodeBlock
            .builder()
            .addStatement("val $propertyName = HashMap<String,Any>()")
            .add(elements.joinToString("\n") {
                val qualifiedName = (it as TypeElement).qualifiedName
                "$propertyName[\"${getServiceName(it)}\"] = $qualifiedName()".trimIndent()
            })
            .add("\nreturn $propertyName")
            .build()

        val funSpec = FunSpec
            .builder(AirRouter.INIT_FUNC_NAME)
            .addCode(codeBlock)
            .returns(
                HashMap::class
                    .asClassName()
                    .parameterizedBy(
                        String::class.asClassName(),
                        Any::class.asTypeName()
                    )
            )
            .build()

        val firstElement = elements.first()
        val pkgName = processingEnv.elementUtils.getPackageOf(firstElement).toString()
        val clsSuffix = pkgName.substring(pkgName.lastIndexOf('.') + 1, pkgName.length)
        val clsName =
            AirRouter.INIT_CLS_NAME + "_" + if (suffix.isNullOrEmpty()) clsSuffix else suffix

        val typeSpec = TypeSpec
            .objectBuilder(clsName)
            .addFunction(funSpec)
            .build()

        val fileSpec = FileSpec
            .builder(
                processingEnv.elementUtils.getPackageOf(firstElement).toString(),
                clsName
            )
            .addType(typeSpec)
            .build()

        fileSpec.writeTo(processingEnv.filer)
        return true
    }
}
