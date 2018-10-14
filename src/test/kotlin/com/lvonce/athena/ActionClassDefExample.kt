package com.lvonce.athena

import com.lvonce.athena.generator.buildAvatar

val avatarConfig = buildAvatar {
    packageName = "com.lvonce.athena.actions"
    avatarClassName = "Person"
    actionClassName = "AvatarActions"
    logicClassName = "AvatarLogic"

    types {
        type {
            name = "Account"
            fields {
                field {
                    name = "id"
                    type = "Long"
                    default = "0L"
                    annotions {
                        annotation { "@Id" }
                    }
                }
            }
        }
    }

    attrs {
        attr {
            name = "attr1"
            type = "Int"
        }
        attr {
            name = "attr2"
            type = "String"
        }
    }

    actions {
        action {
            name = "NoArgFunction"
        }
        action {
            name = "ApplyVacation"
            params {
                param {
                    name = "arg1"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "HasPermission"
            params {
                param {
                    name = "arg1"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "SetNextEntity"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "SendToEntity"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "SendToPermission"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "CheckEntityId"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "Agree"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "Disagree"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }

        action {
            name = "Pass"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "Reject"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
        action {
            name = "CancelFromPermission"
            params {
                param {
                    name = "entityId"
                    type = "Int"
                    default = "23"
                }
                param {
                    name = "arg2"
                    type = "String"
                    default = "\"test\""
                }
            }
        }
    }
}