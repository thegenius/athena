package com.lvonce.athena.actions.impl

import com.lvonce.athena.actions.Person
import com.lvonce.athena.actions.AvatarActions
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

object NoArgFunctionImpl: AvatarActions.NoArgFunction {
    override fun noArgFunction(entity: Person): State {
        return MEANINGLESS
    }
}
