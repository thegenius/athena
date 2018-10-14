package com.lvonce.athena.actions

import com.lvonce.athena.*
import com.lvonce.athena.utils.*
import com.lvonce.athena.action.*
import com.lvonce.athena.actions.Person

class AvatarLogic: LogicPlan<Person, AvatarLogic>() {
    
    var noArgFunction: LogicActionWrapper<Person, Any>?
        by LogicActionWrapperAttr(AvatarActions.noArgFunctionFunc)

    var applyVacation: LogicActionWrapper<Person, AvatarActions.ApplyVacationArg>?
        by LogicActionWrapperAttr(AvatarActions.applyVacationFunc,
            LogicArgParser(AvatarActions.ApplyVacationArg::class.java))

    var hasPermission: LogicActionWrapper<Person, AvatarActions.HasPermissionArg>?
        by LogicActionWrapperAttr(AvatarActions.hasPermissionFunc,
            LogicArgParser(AvatarActions.HasPermissionArg::class.java))

    var setNextEntity: LogicActionWrapper<Person, AvatarActions.SetNextEntityArg>?
        by LogicActionWrapperAttr(AvatarActions.setNextEntityFunc,
            LogicArgParser(AvatarActions.SetNextEntityArg::class.java))

    var sendToEntity: LogicActionWrapper<Person, AvatarActions.SendToEntityArg>?
        by LogicActionWrapperAttr(AvatarActions.sendToEntityFunc,
            LogicArgParser(AvatarActions.SendToEntityArg::class.java))

    var sendToPermission: LogicActionWrapper<Person, AvatarActions.SendToPermissionArg>?
        by LogicActionWrapperAttr(AvatarActions.sendToPermissionFunc,
            LogicArgParser(AvatarActions.SendToPermissionArg::class.java))

    var checkEntityId: LogicActionWrapper<Person, AvatarActions.CheckEntityIdArg>?
        by LogicActionWrapperAttr(AvatarActions.checkEntityIdFunc,
            LogicArgParser(AvatarActions.CheckEntityIdArg::class.java))

    var agree: LogicActionWrapper<Person, AvatarActions.AgreeArg>?
        by LogicActionWrapperAttr(AvatarActions.agreeFunc,
            LogicArgParser(AvatarActions.AgreeArg::class.java))

    var disagree: LogicActionWrapper<Person, AvatarActions.DisagreeArg>?
        by LogicActionWrapperAttr(AvatarActions.disagreeFunc,
            LogicArgParser(AvatarActions.DisagreeArg::class.java))

    var pass: LogicActionWrapper<Person, AvatarActions.PassArg>?
        by LogicActionWrapperAttr(AvatarActions.passFunc,
            LogicArgParser(AvatarActions.PassArg::class.java))

    var reject: LogicActionWrapper<Person, AvatarActions.RejectArg>?
        by LogicActionWrapperAttr(AvatarActions.rejectFunc,
            LogicArgParser(AvatarActions.RejectArg::class.java))

    var cancelFromPermission: LogicActionWrapper<Person, AvatarActions.CancelFromPermissionArg>?
        by LogicActionWrapperAttr(AvatarActions.cancelFromPermissionFunc,
            LogicArgParser(AvatarActions.CancelFromPermissionArg::class.java))


    override fun copy(): AvatarLogic? {
        return CopyUtil.copy(this, AvatarLogic::class.java)
    }
}
