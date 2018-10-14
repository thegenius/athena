package com.lvonce.athena.action

import com.lvonce.athena.LogicPlan
import kotlin.reflect.KProperty

class LogicActionAttr<E, A: LogicActionBase<E>>(val test:Int = 0) {
    var value: A? = null

    operator fun getValue(thisRef: LogicPlan<*, *>?, property: KProperty<*>): A? {
        return value
    }

    operator fun setValue(thisRef: LogicPlan<E, *>?, property: KProperty<*>, value: A?) {
        thisRef?.register(property.name, value?: DefaultLogicAction())
        this.value = value
    }
}