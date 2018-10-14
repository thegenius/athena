package com.lvonce.athena.utils


object IdGenerator {
    private var dataCenterId: Long = 0L
    private var machineId: Long = 0L
    private var snowFlake = SnowFlake(dataCenterId, machineId)
    fun config(dataCenterId: Long, machineId: Long) {
        this.dataCenterId = dataCenterId
        this.machineId = machineId
    }
    fun nextId(): Long = snowFlake.nextId()
}