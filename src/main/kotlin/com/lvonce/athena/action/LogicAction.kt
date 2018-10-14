package com.lvonce.athena.action

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.lvonce.athena.LogicArgParser

interface LogicAction<in E, A>: LogicActionBase<E> {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(LogicAction::class.java)
    }

    /**
     * MEANINGLESS: 只应该出现在动作的返回值中，表示请无视我，我的结果没有意义
     * INIT: 节点出现的状态，表示未执行，或者未执行完成
     * WAITING: 表示节点在等待某个特定的事件发生，需要配合等待的事件名称
     * TRUE: 我执行完任务成功了
     * FALSE: 我执行任务失败了
     * DISCARD: 我遇到了不可恢复的错误，请丢弃整个任务，再执行下去可能有错误
     * */
    enum class State {
        MEANINGLESS,
        INIT,
        TRUE,
        FALSE,
        DISCARD;
        companion object {
            fun negate(state: State): State {
                return when (state) {
                    TRUE -> FALSE
                    FALSE -> TRUE
                    else -> state
                }
            }
            fun of(value: Boolean?): State {
                return when(value) {
                    true -> TRUE
                    else -> FALSE
                }
            }
            fun of(value: Int) : State {
                return when (value) {
                    1 -> INIT
                    2 -> TRUE
                    3 -> FALSE
                    4 -> DISCARD
                    else -> MEANINGLESS
                }
            }
            fun of(value: String) : State {
                return when (value) {
                    "INIT" -> INIT
                    "TRUE" -> TRUE
                    "FALSE" -> FALSE
                    "DISCARD" -> DISCARD
                    else -> MEANINGLESS
                }
            }
        }
    }

    override var id: Long
    override var name: String
    var arg: A?
    var argParser: LogicArgParser<A>?

    /** action执行时调用的函数，此时arg应该是已经解析完毕 */
    override fun run(entity: E): State {
        return execute(entity, this.arg)
    }

    /** event触发时调用的函数，此时还需要解析arg */
    override fun run(entity: E, argStr: String?): State {
        try {
            return execute(entity, argParser?.parseArg(argStr))
        } catch (e: Exception) {
            logger.warn("LogicAction - $name - $id - error - {}", e)
        }
        return State.MEANINGLESS
    }

    fun execute(entity:E, arg: A?): State
}



