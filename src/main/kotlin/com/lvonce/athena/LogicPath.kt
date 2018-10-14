package com.lvonce.athena
import com.lvonce.athena.action.LogicAction.State

data class LogicPath (
    var name: String = "",
    var result: State = State.MEANINGLESS,
    var state: State = State.INIT
)