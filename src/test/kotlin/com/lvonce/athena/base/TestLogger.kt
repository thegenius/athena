package com.lvonce.athena.base

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface TestLogger {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(TestLogger::class.java)
    }
}