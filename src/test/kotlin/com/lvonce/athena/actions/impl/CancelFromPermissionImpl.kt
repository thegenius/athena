package com.lvonce.athena.actions.impl

import com.lvonce.athena.actions.Person
import com.lvonce.athena.actions.AvatarActions
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

object CancelFromPermissionImpl: AvatarActions.CancelFromPermission {
    override fun cancelFromPermission(entity: Person, entityId: Int, arg2: String): State {
        if (entityId == 9527) {
            return TRUE
        }
        return FALSE
    }
}
