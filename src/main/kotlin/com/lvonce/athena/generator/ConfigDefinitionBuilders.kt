package com.lvonce.athena.generator

class ActionParamBuilder(private val parent: ActionParamsBuilder? = null) {
    var name: String = ""
    var type: String = ""
    var default: String = ""

    private val definition
        get() = ActionParam(
                name,
                type,
                default
        )

    fun build(block: ActionParamBuilder.() -> Unit): ActionParam {
        block()
        parent?.params?.add(this.definition)
        return this.definition
    }
}

class ActionParamsBuilder(private val parent: ActionBuilder? = null) {
    val params: MutableList<ActionParam> = mutableListOf()
    private val definition get() = params

    fun param(block: ActionParamBuilder.() -> Unit) = ActionParamBuilder(this).build(block)
    fun build(block: ActionParamsBuilder.() -> Unit): List<ActionParam> {
        block()
        parent?.paramsInner = definition
        return definition
    }
}

class ActionBuilder(private val parent: ActionsBuilder? = null) {
    var name: String = ""
    var paramsInner: MutableList<ActionParam> = mutableListOf()
    fun params(block: ActionParamsBuilder.() -> Unit) = ActionParamsBuilder(this).build(block)
    private val definition
        get() = Action(
                name,
                paramsInner
        )

    fun build(block: ActionBuilder.() -> Unit): Action {
        block()
        parent?.actions?.add(this.definition)
        return this.definition
    }
}

class ActionsBuilder(private val parent: AvatarBuilder? = null) {
    var actions: MutableList<Action> = mutableListOf()
    private val definition get() = actions

    fun action(block: ActionBuilder.() -> Unit) = ActionBuilder(this).build(block)
    fun build(block: ActionsBuilder.() -> Unit): List<Action> {
        block()
        parent?.actionsInner = definition
        return definition
    }
}

class AvatarAttrBuilder(private val parent: AvatarAttrsBuilder? = null) {
    var name: String = ""
    var type: String = ""
    var default: String = ""
    private val definition
        get() = AvatarAttr(
                name,
                type,
                default
        )

    fun build(block: AvatarAttrBuilder.() -> Unit): AvatarAttr {
        block()
        parent?.attrs?.add(this.definition)
        return this.definition
    }
}

class AvatarAttrsBuilder(private val parent: AvatarBuilder? = null) {
    val attrs: MutableList<AvatarAttr> = mutableListOf()
    private val definition get() = attrs

    fun attr(block: AvatarAttrBuilder.() -> Unit) = AvatarAttrBuilder(this).build(block)
    fun build(block: AvatarAttrsBuilder.() -> Unit): List<AvatarAttr> {
        block()
        parent?.attrsInner = definition
        return definition
    }
}

class TypeFieldAnnotationsBuilder(private val parent: TypeFieldBuilder? = null) {
    var annotations: MutableList<String> = mutableListOf()
//    var annotation: String
//        get() = ""
//        set(value){
//            annotations.add(value)
//        }
    private val definition
        get() = annotations
    fun annotation(block: TypeFieldAnnotationsBuilder.() -> String) = annotations.add(block())
    fun build(block: TypeFieldAnnotationsBuilder.() -> Unit): List<String> {
        block()
        parent?.annotationsInner = definition
        return definition
    }
}

class TypeFieldBuilder(private val parent: TypeFieldsBuilder? = null) {
    var name: String = ""
    var type: String = ""
    var default: String = ""
    var annotationsInner: List<String> = listOf()
    private val definition
        get() = TypeField(
                name,
                type,
                default,
                annotationsInner
        )
    fun annotions(block: TypeFieldAnnotationsBuilder.() -> Unit) = TypeFieldAnnotationsBuilder(this).build(block)
    fun build(block: TypeFieldBuilder.() -> Unit): TypeField {
        block()
        parent?.fields?.add(this.definition)
        return this.definition
    }
}

class TypeFieldsBuilder(private val parent: TypeBuilder? = null) {
    val fields: MutableList<TypeField> = mutableListOf()
    private val definition get() = fields

    fun field(block: TypeFieldBuilder.() -> Unit) = TypeFieldBuilder(this).build(block)
    fun build(block: TypeFieldsBuilder.() -> Unit): List<TypeField> {
        block()
        parent?.fieldsInner = definition
        return definition
    }
}

class TypeBuilder(private val parent: TypesBuilder? = null) {
    var name: String = ""
    var annotationsInner: List<String> = listOf()
    var fieldsInner: List<TypeField> = listOf()
    private val definition
        get() = Type(
                name,
                annotationsInner,
                fieldsInner
        )

    fun fields(block: TypeFieldsBuilder.()->Unit) = TypeFieldsBuilder(this).build(block)
    fun build(block: TypeBuilder.() -> Unit): Type {
        block()
        parent?.types?.add(definition)
        return this.definition
    }
}

class TypesBuilder(private val parent: AvatarBuilder? = null) {
    val types: MutableList<Type> = mutableListOf()
    private val definition get() = types

    fun type(block: TypeBuilder.() -> Unit) = TypeBuilder(this).build(block)
    fun build(block: TypesBuilder.() -> Unit): List<Type> {
        block()
        parent?.typesInner = definition
        return definition
    }
}

class AvatarBuilder {
    var packageName: String = ""
    var avatarClassName: String = ""
    var actionClassName: String = ""
    var logicClassName: String = ""
    var typesInner: MutableList<Type> = mutableListOf()
    var attrsInner: MutableList<AvatarAttr> = mutableListOf()
    var actionsInner: MutableList<Action> = mutableListOf()
    val definition
        get() = Avatar(
                packageName,
                avatarClassName,
                actionClassName,
                logicClassName,
                typesInner,
                attrsInner,
                actionsInner
        )

    fun types(block: TypesBuilder.()->Unit) = TypesBuilder(this).build(block)
    fun attrs(block: AvatarAttrsBuilder.() -> Unit) = AvatarAttrsBuilder(this).build(block)
    fun actions(block: ActionsBuilder.() -> Unit) = ActionsBuilder(this).build(block)
}

fun buildAvatar(block: AvatarBuilder.() -> Unit): Avatar {
    val builder = AvatarBuilder()
    builder.block()
    return builder.definition
}