package com.myth.arch.airrouter.processor

import com.google.auto.service.AutoService
import com.myth.arch.airrouter.AirConfig
import com.myth.arch.airrouter.AirRouter
import com.myth.arch.airrouter.AirService
import com.myth.arch.airrouter.processor.generator.AirConfigGenerator
import com.myth.arch.airrouter.processor.generator.AirInitGenerator
import com.myth.arch.airrouter.processor.generator.AirServiceGenerator
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class AirRouterProcessor : AbstractProcessor() {
    private lateinit var airConfigGenerator: AirConfigGenerator
    private lateinit var airInitGenerator: AirInitGenerator
    private lateinit var airServiceGenerator: AirServiceGenerator

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        airConfigGenerator = AirConfigGenerator(processingEnv)
        airInitGenerator = AirInitGenerator(processingEnv)
        airServiceGenerator = AirServiceGenerator(processingEnv)
    }

    override fun process(
        typeElements: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {

        val amElements = roundEnv.getElementsAnnotatedWith(AirConfig::class.java)
        val asElements = roundEnv.getElementsAnnotatedWith(AirService::class.java)

        airConfigGenerator.process(amElements)
        airInitGenerator.process(asElements, airConfigGenerator.suffix)
        if (AirRouter.isAutoGenerateServices()) {
            airServiceGenerator.process(asElements, airConfigGenerator.suffix)
        }
        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(
            AirConfig::class.java.name,
            AirService::class.java.name
        )
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }
}