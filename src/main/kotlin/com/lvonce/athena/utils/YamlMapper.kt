package com.lvonce.athena.utils

import org.slf4j.LoggerFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.DeserializationFeature

object YamlMapper {

    private val logger = LoggerFactory.getLogger(YamlMapper::class.java)
    private val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule())


    init {
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    fun <T>writeValueAsString(value: T): String {
        return mapper.writeValueAsString(value)
    }

    fun <T>writeValueAsString(value: T?, default: T): String {
        return mapper.writeValueAsString(value?:default)
    }

    fun <T>readValue(value: String, clazz: Class<T>): T? {
        try {
            return mapper.readValue(value, clazz)
        } catch (e: Exception) {
            logger.warn("readValue - error - ", e)
        }
        return null
    }

    fun <T>readValue(value: String, clazz: Class<T>, default: T): T {
        try {
            return mapper.readValue(value, clazz)
        } catch (e: Exception) {
            logger.info("readValue - default")
        }
        return default
    }
}