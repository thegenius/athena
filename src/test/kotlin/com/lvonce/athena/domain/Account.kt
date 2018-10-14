package com.lvonce.athena.actions

import javax.persistence.*
import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonIgnore

class Account {
    @Id
    var id: Long = 0L
}
