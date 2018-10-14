package com.lvonce.athena

import com.lvonce.athena.action.LogicAction
import com.lvonce.athena.action.LogicActionBase
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

abstract class LogicPlan<E, P> {
    private var state: State = INIT
    private val actions = LinkedHashMap<String, LogicActionBase<E>>()

    private var _name: String = ""
    var name: String
        get() = _name
        set(value) {
            _name = value
        }

    private var _condition: String = ""
    var condition: String
        get() = _condition
        set(value) {
            _condition = value
        }

    private var _event: String = ""
    var event: String
        get() = _event
        set(value) {
            clean()
            _event = value
        }

    private var _action: String = ""
    var action: String
        get() = _action
        set(value) {
            clean()
            _action = value
        }

    private var _store: Boolean = true
    var store: Boolean
        get() = _store
        set(value) {
            _store = value
        }

    private var _count: Int = 1
    var count: Int
        get() = _count
        set(value) {
            _count = value
        }

    private var _not: P? = null
    var not: P?
        get() = _not
        set(value) {
            clean()
            _not = value
        }

    private var _sequence: List<P>? = null
    var sequence: List<P>?
        get() = _sequence
        set(value) {
            clean()
            _sequence = value
        }

    private var _selector: List<P>? = null
    var selector: List<P>?
        get() = _selector
        set(value) {
            clean()
            _selector = value
        }

    private fun clean() {
        _event = ""
        _action = ""
        _not = null
        _sequence = null
        _selector = null
    }

    fun state(): State {
        return this.state
    }

    fun transferTo(newState: State) {
        this.state = newState
    }

    fun register(name: String, action: LogicActionBase<E>) {
        this.actions[name] = action
    }

    fun execute(entity: E, name: String): LogicAction.State {
        return this.actions[name]?.run(entity) ?: LogicAction.State.MEANINGLESS
    }

    fun trigger(entity: E, name: String, arg: String?): LogicAction.State {
        val result = this.actions[name]?.run {
            this.run(entity, arg)
        }
        return result ?: LogicAction.State.MEANINGLESS
    }

    abstract fun copy(): P?
}