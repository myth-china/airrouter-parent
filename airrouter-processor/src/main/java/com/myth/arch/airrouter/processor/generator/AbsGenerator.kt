package com.myth.arch.airrouter.processor.generator

import java.lang.IllegalStateException
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Name
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

abstract class AbsGenerator(private val processingEnv: ProcessingEnvironment) {

    fun checkKind(elements: Set<Element>, kind: ElementKind): Boolean {
        if (elements.isEmpty()) {
            return false
        }

        if (elements.any { it.kind != kind }) {
            val errElement = elements.filter { it.kind != kind }.joinToString { it.simpleName }
            val errMsg = "$errElement kind error, must be ${kind.name}"
            processingEnv.messager.printMessage(
                Diagnostic.Kind.ERROR,
                errMsg
            )
            throw IllegalStateException(errMsg)
        }

        return true
    }

    fun getServiceName(element: Element): Name {
        return (element as TypeElement).qualifiedName!!
    }
}