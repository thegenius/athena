package com.lvonce.athena

import com.lvonce.athena.generator.Action
import com.lvonce.athena.generator.AvatarGenerator
import com.lvonce.athena.utils.YamlMapper
import org.junit.Assert
import org.junit.Test

class AvatarGeneratorTest {
    val logicActionGenerator = AvatarGenerator()

    @Test
    fun testLoad() {
        var str = "{" +
                "name: SetNextEntity"+
                "}"
        var actionDefinition = YamlMapper.readValue(str, Action::class.java)
        Assert.assertEquals("SetNextEntity", actionDefinition?.name)


        str = "{" +
                "name: SetNextEntity, "+
                "params: [{name: a, type: Int}, {name: b, type: String}]"+
                "}"
        val actionDefinition2 = YamlMapper.readValue(str, Action::class.java)
        Assert.assertNotNull(actionDefinition2)
        actionDefinition2?.apply {
            Assert.assertEquals("SetNextEntity", actionDefinition2.name)
            Assert.assertEquals("a", actionDefinition2.params[0].name)
            Assert.assertEquals("Int", actionDefinition2.params[0].type)
            Assert.assertEquals("", actionDefinition2.params[0].default)
        }
    }

    @Test
    fun testGenParamList() {
        var str = "{" +
                "actionClassName: SetNextEntity, "+
                "params: [{actionClassName: a, type: Int}, {actionClassName: b, type: String}]"+
                "}"
        val actionDefinition = YamlMapper.readValue(str, Action::class.java)
        actionDefinition?.apply {
            println(logicActionGenerator.genParamList(actionDefinition.params))
        }
    }

//    @Test
//    fun testGenActionDef() {
//        var str = "{" +
//                "name: SetNextEntity, "+
//                "params: [{name: a, type: Int}, {name: b, type: String}]"+
//                "}"
//        val def = logicActionGenerator.genActionDef("Person", str)
//        println(def)
//    }
//
//    @Test
//    fun testGenClassDef() {
//        var str = "{" +
//                "packageName: com.lvonce.athena.actions,"+
//                "actionClassName: AvatarActions, "+
//                "avatarPackage: com.lvonce.athena,"+
//                "avatarClassName: Person,"+
//                "actions: [{actionClassName: SetNextEntity, params: [{actionClassName: entityId, type: Int}, {actionClassName: arg2, type: String}]}]"+
//                "}"
//        val def = logicActionGenerator.genActionClass(str)
//        println(def)
//    }

//    @Test
//    fun testParamBuilder() {
//        val param1 = buildParam {
//            name = "hello"
//            type = "String"
//            default = "Test"
//        }
//        Assert.assertEquals("hello", param1.name)
//        Assert.assertEquals("String", param1.type)
//        Assert.assertEquals("Test", param1.default)
//
//        val param2 = buildParam {
//            name = "hello"
//            type = "String"
//        }
//        Assert.assertEquals("hello", param2.name)
//        Assert.assertEquals("String", param2.type)
//        Assert.assertEquals("", param2.default)
//    }
//
//    @Test
//    fun testActionBuilder() {
//        val action1 = buildAction {
//            name = "SetNextEntity"
//            params {
//                param {
//                    name = "hello"
//                    type = "String"
//                }
//            }
//
//        }
//        Assert.assertEquals("SetNextEntity", action1.name)
//        Assert.assertEquals("String", action1.params[0].type)
//        Assert.assertEquals("hello", action1.params[0].name)
//    }


    @Test
    fun testDSL() {
        println(logicActionGenerator.genActionClass(avatarConfig))
    }

    @Test
    fun testParser() {
        val parser: LogicArgParser<Any>? = null
        val result = parser?.parseArg("test")
        println(result)
    }

    @Test
    fun testGenFile() {
        logicActionGenerator.genAvatarFiles(avatarConfig)
    }

}