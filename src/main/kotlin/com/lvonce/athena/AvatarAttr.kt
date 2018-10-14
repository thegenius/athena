package com.lvonce.athena

import kotlin.reflect.KProperty

class AvatarAttr<T> {
    var value: T? = null

    operator fun getValue(thisRef: Avatar?, property: KProperty<*>): T? {
        return value
    }

    operator fun setValue(thisRef: Avatar?, property: KProperty<*>, value: T?) {
        if (value != null) {
            thisRef?.setAttr(property.name, value as Any)
        }
        this.value = value
    }
}