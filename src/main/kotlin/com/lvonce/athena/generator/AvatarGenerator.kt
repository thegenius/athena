package com.lvonce.athena.generator

import com.lvonce.athena.utils.YamlMapper
import java.io.File

class AvatarGenerator {

    fun genAttrList(attrs: List<AvatarAttr>, ignoreDefault: Boolean = false): String {
        val strList = attrs.map { it ->
            if (it.default.isBlank() or ignoreDefault)
                "var ${it.name}: ${it.type}" else "var ${it.name}: ${it.type} = ${it.default}"
        }
        return strList.joinToString("")
    }

    fun genFuncName(name: String): String {
        val first = name.substring(0, 1).toLowerCase()
        val last = name.substring(1)
        return first + last
    }

    fun genParamList(params: List<ActionParam>, ignoreDefault: Boolean = false): String {
        val strList = params.map { it ->
            if (it.default.isBlank() or ignoreDefault)
                "${it.name}: ${it.type}" else "${it.name}: ${it.type} = ${it.default}"
        }
        return strList.joinToString(", ")
    }

    fun genDataParamList(params: List<ActionParam>): String {
        val strList = params.map { it ->
            if (it.default.isBlank())
                "val ${it.name}: ${it.type}" else "val ${it.name}: ${it.type} = ${it.default}"
        }
        return strList.joinToString(", ")
    }

    fun genArgParamList(params: List<ActionParam>): String {
        val strList = params.map { it ->
            "arg.${it.name}"
        }
        return strList.joinToString(", ")
    }

//    fun genActionDef(avatar: String, def: String): String {
//        val actionDef = YamlMapper.readValue(def, Action::class.java)
//        actionDef?: return ""
//        return genActionDef(avatar, actionDef)
//    }
//
//    fun genActionClass(def: String): String {
//        val classDef = YamlMapper.readValue(def, Avatar::class.java)
//        classDef ?: return ""
//        return genActionClass(classDef)
//    }

    fun genActionDef(avatar: String, def: Action): String {
        val funcName = genFuncName(def.name)
        if (def.params.size > 0) {
            val paramList = genParamList(def.params)
            val dataParamList = genDataParamList(def.params)
            val argParamList = genArgParamList(def.params)
            val result = """
    /*********************************************
    ** begin of ${def.name} action definition **/
    data class ${def.name}Arg(${dataParamList})
    interface ${def.name} {
        fun ${funcName}(entity:${avatar}, ${paramList}): LogicAction.State
    }
    private val ${def.name}Action: ${def.name} = ${def.name}Impl
    val ${funcName}Func = fun(entity: ${avatar}, arg: ${def.name}Arg?): LogicAction.State {
        arg?: return LogicAction.State.MEANINGLESS
        return ${def.name}Action.${funcName}(entity, ${argParamList})
    }
    /** end of ${def.name} action definition ****
    ***********************************************/
"""
            return result
        } else {
            val result = """
    /*********************************************
    ** begin of ${def.name} action definition **/
    interface ${def.name} {
        fun ${funcName}(entity:${avatar}): LogicAction.State
    }
    private val ${def.name}Action: ${def.name} = ${def.name}Impl
    val ${funcName}Func = fun(entity: ${avatar}, arg: Any?): LogicAction.State {
        return ${def.name}Action.${funcName}(entity)
    }
    /** end of ${def.name} action definition ****
    ***********************************************/
"""
            return result
        }
    }

    fun genActionImplClass(packageName: String,
                           avatarPackage: String,
                           avatarClassName: String,
                           actionClassName: String,
                           def: Action): String {
        val funName = genFuncName(def.name)
        val paramList = genParamList(def.params, true)
        var fullParamList = "entity: ${avatarClassName}, ${paramList}"
        if (def.params.isEmpty()) {
            fullParamList = "entity: ${avatarClassName}"
        }

        val result = """package ${packageName}.impl

import ${avatarPackage}.${avatarClassName}
import ${packageName}.${actionClassName}
import com.lvonce.athena.action.LogicAction.State
import com.lvonce.athena.action.LogicAction.State.*

object ${def.name}Impl: ${actionClassName}.${def.name} {
    override fun ${funName}(${fullParamList}): State {
        return MEANINGLESS
    }
}
"""
        return result

    }

    fun genActionClass(def: Avatar): String {
        val result = """package ${def.packageName}

import com.lvonce.athena.action.LogicAction
import ${def.packageName}.impl.*
import ${def.packageName}.${def.avatarClassName}

object ${def.actionClassName} {
    ${def.actions.map { genActionDef(def.avatarClassName, it) }.joinToString("")}
}
"""
        return result
    }

    fun genActionRegister(avatarClassName: String,
                          actionClassName: String,
                          def: Action): String {
        val funcName = genFuncName(def.name)
        if (def.params.isNotEmpty()) {
            val result = """
    var ${funcName}: LogicActionWrapper<${avatarClassName}, ${actionClassName}.${def.name}Arg>?
        by LogicActionWrapperAttr(${actionClassName}.${funcName}Func,
            LogicArgParser(${actionClassName}.${def.name}Arg::class.java))
"""
            return result
        } else {
            val result = """
    var ${funcName}: LogicActionWrapper<${avatarClassName}, Any>?
        by LogicActionWrapperAttr(${actionClassName}.${funcName}Func)
"""
            return result
        }
    }

    fun genLogicClass(def: Avatar): String {
        val result = """package ${def.packageName}

import com.lvonce.athena.*
import com.lvonce.athena.utils.*
import com.lvonce.athena.action.*
import ${def.packageName}.${def.avatarClassName}

class ${def.logicClassName}: LogicPlan<${def.avatarClassName}, ${def.logicClassName}>() {
    ${def.actions.map {
            genActionRegister(
                    def.avatarClassName,
                    def.actionClassName,
                    it)
        }.joinToString("")}

    override fun copy(): ${def.logicClassName}? {
        return CopyUtil.copy(this, ${def.logicClassName}::class.java)
    }
}
"""
        return result
    }

    fun genAvatarAttr(def: AvatarAttr): String {
        val result = """    var ${def.name}: ${def.type}
"""
        return result
    }

    fun genAvatarClass(def: Avatar): String {
        val result = """package ${def.packageName}

interface ${def.avatarClassName} {
${def.attrs.map { genAvatarAttr(it) }.joinToString("")}}
"""
        return result
    }

    fun genTypeField(def: TypeField): String {
        val result = """    ${def.annotations.joinToString("    \n\r")}
    var ${def.name}: ${def.type} = ${def.default}
"""
        return result
    }

    fun genTypeClass(packageName: String, def: Type): String {
        val result = """package ${packageName}

import javax.persistence.*
import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonIgnore

class ${def.name} {
${def.fields.map { genTypeField(it) }.joinToString("")}}
"""
        return result
    }

    fun prepareAvatarDirs(filePath: String) {
        val dir = File(filePath)
        dir.mkdirs()

        val implDirPath = "${filePath}/impl"
        val implDir = File(implDirPath)
        implDir.mkdirs()

        val domainDirPath = "${filePath}/domain"
        val domainDir = File(domainDirPath)
        domainDir.mkdirs()
    }

    fun genAvatarFiles(def: Avatar, filePath: String = "./generate") {
        prepareAvatarDirs(filePath)

        val avatarFile = File(filePath, "${def.avatarClassName}.kt")
        avatarFile.writeText(genAvatarClass(def))

        val actionFile = File(filePath, "${def.actionClassName}.kt")
        actionFile.writeText(genActionClass(def))

        val logicFile = File(filePath, "${def.logicClassName}.kt")
        logicFile.writeText(genLogicClass(def))

        val domainDirPath = "${filePath}/domain"
        def.types.forEach {
            val file = File(domainDirPath, "${it.name}.kt")
            file.writeText(genTypeClass(def.packageName, it))
        }

        val implDirPath = "${filePath}/impl"
        def.actions.forEach {
            val file = File(implDirPath, "${it.name}Impl.kt")
            file.writeText(genActionImplClass(
                    def.packageName,
                    def.packageName,
                    def.avatarClassName,
                    def.actionClassName,
                    it))
        }

    }

}