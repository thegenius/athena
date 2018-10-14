package com.lvonce.athena.utils

import io.protostuff.ProtostuffIOUtil
import io.protostuff.LinkedBuffer
import io.protostuff.runtime.RuntimeSchema

class CopyUtil {
    companion object {
        fun <T> copy(obj: T, clazz: Class<T>): T? {
            // Re-use (manage) this buffer to avoid allocating on every serialization
            val buffer = LinkedBuffer.allocate(512)
            val schema = RuntimeSchema.getSchema(clazz)

            var copyedObj: T? = null
            try {
                // serialization
                val protostuff: ByteArray = ProtostuffIOUtil.toByteArray(obj, schema, buffer)

                // deserialization
                copyedObj = schema.newMessage()
                ProtostuffIOUtil.mergeFrom(protostuff, copyedObj, schema)
            } finally {
                buffer.clear()
            }
            return copyedObj
        }
    }
}