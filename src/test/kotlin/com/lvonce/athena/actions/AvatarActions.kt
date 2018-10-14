package com.lvonce.athena.actions

import com.lvonce.athena.action.LogicAction
import com.lvonce.athena.actions.impl.*
import com.lvonce.athena.actions.Person

object AvatarActions {
    
    /*********************************************
    ** begin of NoArgFunction action definition **/
    interface NoArgFunction {
        fun noArgFunction(entity:Person): LogicAction.State
    }
    private val NoArgFunctionAction: NoArgFunction = NoArgFunctionImpl
    val noArgFunctionFunc = fun(entity: Person, arg: Any?): LogicAction.State {
        return NoArgFunctionAction.noArgFunction(entity)
    }
    /** end of NoArgFunction action definition ****
    ***********************************************/

    /*********************************************
    ** begin of ApplyVacation action definition **/
    data class ApplyVacationArg(val arg1: Int = 23, val arg2: String = "test")
    interface ApplyVacation {
        fun applyVacation(entity:Person, arg1: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val ApplyVacationAction: ApplyVacation = ApplyVacationImpl
    val applyVacationFunc = fun(entity: Person, arg: ApplyVacationArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return ApplyVacationAction.applyVacation(entity, arg.arg1, arg.arg2)
    }
    /** end of ApplyVacation action definition ****
    ***********************************************/

    /*********************************************
    ** begin of HasPermission action definition **/
    data class HasPermissionArg(val arg1: Int = 23, val arg2: String = "test")
    interface HasPermission {
        fun hasPermission(entity:Person, arg1: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val HasPermissionAction: HasPermission = HasPermissionImpl
    val hasPermissionFunc = fun(entity: Person, arg: HasPermissionArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return HasPermissionAction.hasPermission(entity, arg.arg1, arg.arg2)
    }
    /** end of HasPermission action definition ****
    ***********************************************/

    /*********************************************
    ** begin of SetNextEntity action definition **/
    data class SetNextEntityArg(val entityId: Int = 23, val arg2: String = "test")
    interface SetNextEntity {
        fun setNextEntity(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val SetNextEntityAction: SetNextEntity = SetNextEntityImpl
    val setNextEntityFunc = fun(entity: Person, arg: SetNextEntityArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return SetNextEntityAction.setNextEntity(entity, arg.entityId, arg.arg2)
    }
    /** end of SetNextEntity action definition ****
    ***********************************************/

    /*********************************************
    ** begin of SendToEntity action definition **/
    data class SendToEntityArg(val entityId: Int = 23, val arg2: String = "test")
    interface SendToEntity {
        fun sendToEntity(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val SendToEntityAction: SendToEntity = SendToEntityImpl
    val sendToEntityFunc = fun(entity: Person, arg: SendToEntityArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return SendToEntityAction.sendToEntity(entity, arg.entityId, arg.arg2)
    }
    /** end of SendToEntity action definition ****
    ***********************************************/

    /*********************************************
    ** begin of SendToPermission action definition **/
    data class SendToPermissionArg(val entityId: Int = 23, val arg2: String = "test")
    interface SendToPermission {
        fun sendToPermission(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val SendToPermissionAction: SendToPermission = SendToPermissionImpl
    val sendToPermissionFunc = fun(entity: Person, arg: SendToPermissionArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return SendToPermissionAction.sendToPermission(entity, arg.entityId, arg.arg2)
    }
    /** end of SendToPermission action definition ****
    ***********************************************/

    /*********************************************
    ** begin of CheckEntityId action definition **/
    data class CheckEntityIdArg(val entityId: Int = 23, val arg2: String = "test")
    interface CheckEntityId {
        fun checkEntityId(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val CheckEntityIdAction: CheckEntityId = CheckEntityIdImpl
    val checkEntityIdFunc = fun(entity: Person, arg: CheckEntityIdArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return CheckEntityIdAction.checkEntityId(entity, arg.entityId, arg.arg2)
    }
    /** end of CheckEntityId action definition ****
    ***********************************************/

    /*********************************************
    ** begin of Agree action definition **/
    data class AgreeArg(val entityId: Int = 23, val arg2: String = "test")
    interface Agree {
        fun agree(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val AgreeAction: Agree = AgreeImpl
    val agreeFunc = fun(entity: Person, arg: AgreeArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return AgreeAction.agree(entity, arg.entityId, arg.arg2)
    }
    /** end of Agree action definition ****
    ***********************************************/

    /*********************************************
    ** begin of Disagree action definition **/
    data class DisagreeArg(val entityId: Int = 23, val arg2: String = "test")
    interface Disagree {
        fun disagree(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val DisagreeAction: Disagree = DisagreeImpl
    val disagreeFunc = fun(entity: Person, arg: DisagreeArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return DisagreeAction.disagree(entity, arg.entityId, arg.arg2)
    }
    /** end of Disagree action definition ****
    ***********************************************/

    /*********************************************
    ** begin of Pass action definition **/
    data class PassArg(val entityId: Int = 23, val arg2: String = "test")
    interface Pass {
        fun pass(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val PassAction: Pass = PassImpl
    val passFunc = fun(entity: Person, arg: PassArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return PassAction.pass(entity, arg.entityId, arg.arg2)
    }
    /** end of Pass action definition ****
    ***********************************************/

    /*********************************************
    ** begin of Reject action definition **/
    data class RejectArg(val entityId: Int = 23, val arg2: String = "test")
    interface Reject {
        fun reject(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val RejectAction: Reject = RejectImpl
    val rejectFunc = fun(entity: Person, arg: RejectArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return RejectAction.reject(entity, arg.entityId, arg.arg2)
    }
    /** end of Reject action definition ****
    ***********************************************/

    /*********************************************
    ** begin of CancelFromPermission action definition **/
    data class CancelFromPermissionArg(val entityId: Int = 23, val arg2: String = "test")
    interface CancelFromPermission {
        fun cancelFromPermission(entity:Person, entityId: Int = 23, arg2: String = "test"): LogicAction.State
    }
    private val CancelFromPermissionAction: CancelFromPermission = CancelFromPermissionImpl
    val cancelFromPermissionFunc = fun(entity: Person, arg: CancelFromPermissionArg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return CancelFromPermissionAction.cancelFromPermission(entity, arg.entityId, arg.arg2)
    }
    /** end of CancelFromPermission action definition ****
    ***********************************************/

}
