package com.myth.arch.airrouter.processor.utils

import java.io.File
import javax.annotation.processing.ProcessingEnvironment

internal object FileUtils {
    private const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

    fun getGenerateCodeDir(processingEnv: ProcessingEnvironment): String? {
        return processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
    }

    fun getPkgProjectDir(pkgName: String): String {
        return pkgName.substring(
            pkgName.lastIndexOf('.') + 1,
            pkgName.length
        )
    }

    fun replaceProjectDir(projectDir: String, dir: String): String {
        var i = -1
        val paths = dir.split("/").toMutableList()
        paths.forEachIndexed { index, s ->
            if (s == "build") {
                i = index - 1
            }
        }
        paths[i] = projectDir
        val destDir = paths.joinToString("/") { it }
        println(destDir)
        return destDir
    }

    fun getJarProjectDir(jarDir: String): String? {
        var preDir = ""

        jarDir.split('/').forEachIndexed { index, s ->
            if (s == "build") {
                return preDir
            }

            preDir = s
        }

        return null
    }

    fun getJarFile(interfaceCls: Class<*>): File {
        return File(interfaceCls.protectionDomain.codeSource.location.path)
    }
}