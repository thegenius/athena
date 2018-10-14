package com.lvonce.athena.actions.impl

import com.lvonce.athena.actions.Person
import com.lvonce.athena.actions.AvatarActions
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

object HasPermissionImpl: AvatarActions.HasPermission {
    override fun hasPermission(entity: Person, arg1: Int, arg2: String): State {
        if (arg1 == 25) {
            return TRUE
        }
        return FALSE
    }
}
