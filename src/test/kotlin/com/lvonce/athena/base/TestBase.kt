package com.lvonce.athena.base

import org.junit.Test
import java.io.InputStream

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

abstract class TestBase<T:TestCaseBase> {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TestBase::class.java)
    }

    val yamlFactory = YAMLFactory()
    val mapper: ObjectMapper = ObjectMapper(yamlFactory)
    var cases: List<T>? = null
    fun load(path: String, typeReference: TypeReference<List<T>>) {
        val input = TestBase::class.java.classLoader.getResourceAsStream(path)
        this.cases = mapper.readValue(input, typeReference)
    }

    fun loadAsStream(path: String): InputStream {
        return TestBase::class.java.classLoader.getResourceAsStream(path)
    }

    @Test
    fun test() {
        val passCount = 0
        this.cases?.forEach {
            run(it)
            logger.info("[${it.caseName}] passed - ${it.caseDesc}")
        }
    }
    abstract fun run(case: T)
}