package com.lvonce.athena

import com.fasterxml.jackson.databind.ObjectMapper

class LogicArgParser<out A> (private val clazz: Class<A>) {
    companion object {
        val mapper = ObjectMapper()
    }
    fun parseArg(argStr: String?): A? {
        return mapper.readValue(argStr, clazz)
    }
}