package com.lvonce.athena.action

import com.lvonce.athena.LogicArgParser
import com.lvonce.athena.LogicPlan
import kotlin.reflect.KProperty

class LogicActionWrapperAttr<E, A>
    (private val executeFunc:((E, A?)-> LogicAction.State),
     private val argParser: LogicArgParser<A>? = null) {
    var value: LogicActionWrapper<E, A>? = null

    operator fun getValue(thisRef: LogicPlan<*, *>?, property: KProperty<*>): LogicActionWrapper<E, A>? {
        return value
    }

    operator fun setValue(thisRef: LogicPlan<E, *>?, property: KProperty<*>, value: LogicActionWrapper<E, A>?) {
        value?.func = executeFunc
        value?.argParser = argParser
        thisRef?.register(property.name, value?: DefaultLogicAction())
        this.value = value
    }
}