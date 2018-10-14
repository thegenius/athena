package com.lvonce.athena.generator

data class ActionParam(
        val name: String,
        val type: String,
        val default: String = ""
)

data class AvatarAttr(
        val name: String,
        val type: String,
        val default: String = ""
)

data class TypeField(
        val name: String,
        val type: String,
        val default: String,
        val annotations: List<String> = listOf()
)

data class Action(
        val name: String,
        val params: List<ActionParam> = listOf()
)

data class Type(
        val name: String,
        val annotations: List<String> = listOf(),
        val fields: List<TypeField> = listOf()
)

data class Avatar(
        val packageName: String,
        val avatarClassName: String,
        val actionClassName: String,
        val logicClassName: String,
        val types: List<Type> = listOf(),
        val attrs: List<AvatarAttr> = listOf(),
        val actions: List<Action> = listOf()
)