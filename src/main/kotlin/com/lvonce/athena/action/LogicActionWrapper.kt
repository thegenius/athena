package com.lvonce.athena.action

import com.lvonce.athena.LogicArgParser
import com.lvonce.athena.utils.IdGenerator
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

class LogicActionWrapper<E, A>: LogicAction<E, A> {
    override var id: Long = IdGenerator.nextId()
    override var name: String = ""
    override var arg: A? = null
    override var argParser: LogicArgParser<A>? = null
    var func: ((E, A?)->State)? = null

    override fun execute(entity: E, arg: A?): State {
        return this.func?.invoke(entity, arg)?:MEANINGLESS
    }
}