package com.lvonce.athena.action

class DefaultLogicAction<in E>: LogicActionBase<E> {
    override var name: String = "__default__"
    override var id: Long = 0L
    override fun run(entity: E): LogicAction.State {
        return LogicAction.State.MEANINGLESS
    }

    override fun run(entity: E, argStr: String?): LogicAction.State {
        return LogicAction.State.MEANINGLESS
    }
}
