package com.lvonce.athena.action


interface LogicActionBase<in E> {

    var id: Long
    var name: String

    /** action执行时调用的函数，此时参数已经解析完毕 */
    fun run(entity: E): LogicAction.State

    /** event触发时调用的函数，此时还需要解析参数 */
    fun run(entity: E, argStr: String?): LogicAction.State

}