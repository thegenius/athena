package com.lvonce.athena

import com.lvonce.athena.utils.IdGenerator


class AvatarExecutor<E : Avatar, P : LogicPlan<E, P>>(private val logicClass: Class<P>) {
    private var logicParser: LogicPlanParser<P> = LogicPlanParser()
    private val logicExecutor = LogicExecutor<E, P>()

    fun execute(entity: E, plan: String, eventName: String = "", eventArgs: String = ""): LogicExecutor.Result<E, P> {
        val plan = logicParser.parsePlan(plan, logicClass)
        val result = logicExecutor.execute(entity, plan, entity.entityId
                ?: 0L, IdGenerator.nextId(), eventName, eventArgs)
        return result
    }
}