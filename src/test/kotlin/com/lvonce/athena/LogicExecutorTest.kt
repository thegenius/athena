package com.lvonce.athena

import com.fasterxml.jackson.core.type.TypeReference
import com.lvonce.athena.base.TestBase
import com.lvonce.athena.base.TestCaseBase
import com.lvonce.athena.actions.AvatarLogic
import com.lvonce.athena.actions.Person
import org.junit.Assert
import org.junit.Before



class LogicExecutorTest: TestBase<LogicExecutorTest.TestCase>() {
    class TestCase: TestCaseBase() {
        var planFilePath: String = ""
        var expected: List<LogicPath> = ArrayList()
    }
    class TestCaseType: TypeReference<List<TestCase>>()

    var logicParser: LogicPlanParser<AvatarLogic> = LogicPlanParser()
    val logicExecutor = LogicExecutor<Person, AvatarLogic>()
    val person = Person()

    @Before
    fun setup() {
        this.load("logic/test_logic_executor.yaml", TestCaseType())
    }

    override fun run(case: TestCase) {
        val logicPlanStream = loadAsStream(case.planFilePath)
        val logicPlan = logicParser.parsePlan(logicPlanStream, AvatarLogic::class.java)
        val str = logicParser.dumpPlan(logicPlan)
        logger.info("$str")
        val copyPlan = logicPlan.copy()
        val result = logicExecutor.execute(person, logicPlan, 23, 123)
        Assert.assertEquals(case.expected, result.executePath)

        if (copyPlan != null) {
            val result = logicExecutor.execute(person, copyPlan, 23, 123)
            Assert.assertEquals(case.expected, result.executePath)
        }
    }
}