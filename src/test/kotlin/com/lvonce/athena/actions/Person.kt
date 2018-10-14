package com.lvonce.athena.actions

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Person {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(Person::class.java)
    }

    fun hello() {
        logger.info("hello person")
    }
}