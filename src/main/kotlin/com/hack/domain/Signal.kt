package com.hack.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "signals", uniqueConstraints=[(UniqueConstraint(columnNames = ["routeId"]))])
class Signal (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @get: NotBlank
    val routeName: String = ""
)