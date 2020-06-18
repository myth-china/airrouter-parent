package com.myth.arch.airrouter.processor.utils

import java.lang.Exception
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

object ClsUtils {

    private fun interfacesName(
        processingEnv: ProcessingEnvironment,
        element: Element
    ): List<String> {
        return (element as TypeElement).interfaces.map {
            it.toString()
        }
    }

    fun interfacesClass(processingEnv: ProcessingEnvironment, element: Element): List<Class<*>> {
        val list = ArrayList<Class<*>>()

        interfacesName(
            processingEnv,
            element
        ).map {
            try {
                list.add(Class.forName(it))
            } catch (e: Exception) {
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, e.localizedMessage)
            }
        }

        return list
    }
}