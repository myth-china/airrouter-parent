package com.myth.arch.airrouter.processor.generator

import com.myth.arch.airrouter.AirConfig
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class AirConfigGenerator(private val processingEnv: ProcessingEnvironment) :
    AbsGenerator(processingEnv) {

    var suffix: String? = null

    fun process(elements: Set<Element>): Boolean {
        if (!checkKind(elements, ElementKind.CLASS)) {
            return false
        }

        val firstElement = elements.first()
        val firstTypeElement = firstElement as TypeElement

        if (elements.size > 1) {
            processingEnv.messager.printMessage(
                Diagnostic.Kind.WARNING,
                "一个模块只能有一个 AirModule 注解，请检查${firstTypeElement.qualifiedName}所在模块"
            )
        }

        suffix = firstElement.getAnnotation(AirConfig::class.java).classNameSuffix

        return true
    }
}