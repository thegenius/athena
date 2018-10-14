package com.lvonce.athena.actions.impl

import com.lvonce.athena.actions.Person
import com.lvonce.athena.actions.AvatarActions
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

object ApplyVacationImpl: AvatarActions.ApplyVacation {
    override fun applyVacation(entity: Person, arg1: Int, arg2: String): State {
        if (arg1 == 23) {
            return TRUE
        }
        return MEANINGLESS
    }
}
