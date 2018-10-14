package com.lvonce.athena

import java.io.InputStream
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory


class LogicPlanParser<P> {
    private val yamlFactory = YAMLFactory()
    private val mapper = ObjectMapper(yamlFactory)
    private val jsonMapper = ObjectMapper()
    constructor() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    fun parsePlan(logicConfig: String, clazz: Class<P>): P {
        return mapper.readValue(logicConfig, clazz)
    }

    fun parsePlanFromJson(logicConfig: String, clazz: Class<P>): P {
        return jsonMapper.readValue(logicConfig, clazz)
    }

    fun parsePlan(logicConfig: InputStream, clazz: Class<P>): P {
        return mapper.readValue(logicConfig, clazz)
    }

    fun dumpPlan(plan: P): String {
        return mapper.writeValueAsString(plan)
    }
}