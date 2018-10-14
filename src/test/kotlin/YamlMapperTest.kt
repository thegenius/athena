import com.lvonce.athena.utils.YamlMapper
import org.junit.Assert

import org.junit.Test

class YamlMapperTest {
    data class DataTest1(val a: Int, val hello: String)
    data class DataTest2(val a: Int? = null, val hello: String? = null)
    data class DataTest3(val a: Int = 12, val hello: String = "world")
    val yamlMapper = YamlMapper

    @Test
    fun testRead() {
        val str = "{a: 12, hello: 'world'}"
        val dataTest:DataTest1? = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNotNull(dataTest)
        dataTest?.let {
            Assert.assertEquals(12, dataTest.a)
            Assert.assertEquals("world", dataTest.hello)
            println(yamlMapper.writeValueAsString(dataTest))
        }
    }

    @Test
    fun testWrite() {
        val dataTest = DataTest1(23, "test")
        val str = yamlMapper.writeValueAsString(dataTest)
        val expectedStr = """
            ---
            a: 23
            hello: "test"

            """.trimIndent()
        Assert.assertEquals(expectedStr, str)

    }

    @Test
    fun testReadInvalid() {
        var str = "{a: 12, hello: }"
        var dataTest:DataTest1? = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNull(dataTest)

        str = "{a: 12}"
        dataTest = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNull(dataTest)

        str = "{}"
        dataTest = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNull(dataTest)

        str = "{"
        dataTest = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNull(dataTest)

        str = ""
        dataTest = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNull(dataTest)
    }

    @Test
    fun testReadUnknown() {
        var str = "{a: 12, hello: world, test: 'test'}"
        var dataTest: DataTest1? = yamlMapper.readValue(str, DataTest1::class.java)
        Assert.assertNotNull(dataTest)
        dataTest?.let {
            Assert.assertEquals(12, dataTest.a)
            Assert.assertEquals("world", dataTest.hello)
        }
    }

    @Test
    fun testReadDefault() {
        var str = "{"
        var dataTest: DataTest2 = yamlMapper.readValue(str, DataTest2::class.java, DataTest2())
        Assert.assertNotNull(dataTest)
        Assert.assertEquals(null, dataTest.a)
        Assert.assertEquals(null, dataTest.hello)

        var dataTest3: DataTest3 = yamlMapper.readValue(str, DataTest3::class.java, DataTest3())
        Assert.assertNotNull(dataTest3)
        Assert.assertEquals(12, dataTest3.a)
        Assert.assertEquals("world", dataTest3.hello)
    }

    @Test
    fun testWriteDefault() {
        var dataTest = DataTest2()

        var str = yamlMapper.writeValueAsString(null, dataTest)
        var expectedStr = """
            ---
            a: null
            hello: null

            """.trimIndent()
        Assert.assertEquals(expectedStr, str)

        var dataTest2 = DataTest2(12, "test")
        str = yamlMapper.writeValueAsString(dataTest2, dataTest)
        expectedStr = """
            ---
            a: 12
            hello: "test"

            """.trimIndent()
        Assert.assertEquals(expectedStr, str)
    }
}