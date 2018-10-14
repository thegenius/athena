package com.lvonce.athena

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.fasterxml.jackson.databind.ObjectMapper

interface Avatar {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Avatar::class.java)
        val objectMapper= ObjectMapper()
    }

    var entityId: Long?
    var attrs: MutableMap<String, Any>

    fun execute(planName: String, planId: Long, eventName: String, eventArgs: String, snapshots: List<String>): Map<String, Any?>

    fun setAttr(name: String, attr: Any) {
        attrs[name] = attr
    }

    fun getAttr(name: String): Any? {
        return attrs[name]
    }

    fun snapshot(attrNames: List<String>): MutableMap<String, Any?> {
        val result: MutableMap<String, Any?> = LinkedHashMap()
        attrNames.forEach{
            result[it] = this.getAttr(it)
        }
        return result
    }
}