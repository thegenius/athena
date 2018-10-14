package com.lvonce.athena

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.lvonce.athena.action.LogicAction
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*
import com.lvonce.athena.utils.IdGenerator

class LogicExecutor<E, P:LogicPlan<E, P>> {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(LogicExecutor::class.java)
    }


    /**
     * 正在等待的结果
     */
    data class Result<E, P:LogicPlan<E, P>>(
            var state: State,
            var waitingEventName: String,
            var nextLogic: P?,
            var executePath: MutableList<LogicPath>)

    /**
     * @param entity:
     * @param plan:
     * @param entityId:
     * @param traceId
     * @param event
     * @param eventArgs
     * @return: 返回整体的执行结果
     * */
    fun execute(entity: E,
                plan: P,
                entityId: Long,
                traceId: Long = IdGenerator.nextId(),
                event: String = "",
                eventArgs: String? = ""): Result<E, P> {
        val finalResult = Result(INIT, "", plan, ArrayList())
        val result = run(entity, plan, finalResult, entityId, traceId, event, eventArgs)
        finalResult.state = result
        if (finalResult.waitingEventName.isBlank()) {
            finalResult.nextLogic = null
        }
        logger.info("$entityId - $traceId - final result - {}", finalResult)
        return finalResult
    }

    private fun store (plan: P, state: State) {
        if (plan.store && (state != State.MEANINGLESS)) {
            plan.transferTo(state)
        }
    }

    private fun run(entity: E,
                    plan: P,
                    finalResult: Result<E, P>,
                    entityId: Long = 0,
                    traceId: Long = IdGenerator.nextId(),
                    event: String = "",
                    eventArgs: String? = ""): LogicAction.State {

        /* 不等于INIT表示已经有执行结果了，直接返回执行完成的结果 */
        if (plan.state() != State.INIT) {
            logger.debug("$entityId - $traceId - leave - ${plan.state()}")
            finalResult.executePath.add(LogicPath(plan.name, plan.state(), plan.state()))
            return plan.state()
        }

        /* 前置条件不为空的时候，先判断前置条件是否满足 */
        if (plan.condition.isNotBlank()) {
            val result = plan.execute(entity, plan.condition)
            logger.debug("$entityId - $traceId - condition - ${plan.name} - ${plan.condition} - $result")
            if (result != TRUE) {
                /* 前置条件失败，视为节点执行失败，但是不会存储在节点状态上 */
                finalResult.executePath.add(LogicPath(plan.name, FALSE, plan.state()))
                return FALSE
            }
        }

        /* 如果有需要等待的事件， 则执行事件*/
        if (plan.event.isNotBlank()) {
            logger.debug("$entityId - $traceId - plan waiting event - ${plan.event}")

            /* 如果本次没有传入事件 或者 如果传入的事件和期待的事件不相符 */
            if (plan.event != event) {
                finalResult.waitingEventName = plan.event
                finalResult.executePath.add(LogicPath(plan.name, State.INIT, plan.state()))
                logger.debug("$entityId - $traceId - plan waiting event unfinished - ${plan.event}")
                return State.INIT
            }

            /* 如果传入的事件符合等待，触发事件处理 */
            logger.debug("$entityId - $traceId - event enter - $event")
            val result = plan.trigger(entity, event, eventArgs)
            logger.debug("$entityId - $traceId - event leave - $event - $result")
            store(plan, result)
            logger.debug("$entityId - $traceId - plan finish event - ${plan.event} - $result")
            finalResult.executePath.add(LogicPath(plan.name, result, plan.state()))
            return result
        }

        /* 如果本节点是一个动作节点，则执行动作 */
        if (plan.action.isNotBlank()) {
            logger.debug("$entityId - $traceId - enter action - ${plan.action} - ${plan.state()}")
            val result = plan.execute(entity, plan.action)
            store(plan, result)
            logger.debug("$entityId - $traceId - leave action - ${plan.action} - $result - ${plan.store}")
            finalResult.executePath.add(LogicPath(plan.name, result, plan.state()))
            return result
        }

        /* 取反节点 */
        if (plan.not != null) {
            logger.debug("$entityId - $traceId - enter not")
            val oringResult = plan.not?.run {
                run(entity, this, finalResult, entityId, traceId, event, eventArgs)
            }
            val result = oringResult?:State.MEANINGLESS
            val negativeResult = LogicAction.State.negate(result)
            store(plan, negativeResult)
            logger.debug("$entityId - $traceId - leave not - $negativeResult - ${plan.store}")
            finalResult.executePath.add(LogicPath(plan.name, negativeResult, plan.state()))
            return negativeResult
        }

        /* 顺序节点 */
        if (plan.sequence != null) {
            logger.debug("$entityId - $traceId - enter sequence")
            var result = State.INIT
            run sequence@{
                plan.sequence?.forEach {
                    result = run(entity, it, finalResult, entityId, traceId, event, eventArgs)
                    if ((result == State.FALSE) ||
                            (result == State.INIT) ||
                            (result == State.DISCARD)) {
                        return@sequence
                    }
                }
            }
            logger.debug("$entityId - $traceId - leave sequence - $result - ${plan.store}")
            store(plan, result)
            return result
        }

        /* 选择节点 */
        if (plan.selector != null) {
            logger.debug("$entityId - $traceId - enter selector")
            var result = State.INIT
            run selector@{
                plan.selector?.forEach {
                    result = run(entity, it, finalResult, entityId, traceId, event, eventArgs)
                    if ((result == State.TRUE) ||
                            (result == State.INIT) ||
                            (result == State.DISCARD)) {
                        return@selector
                    }
                }
            }
            logger.debug("$entityId - $traceId - leave selector - $result")
            store(plan, result)
            return result
        }

        /* 默认什么都没有发生的情况下返回 INIT */
        return State.INIT
    }
}