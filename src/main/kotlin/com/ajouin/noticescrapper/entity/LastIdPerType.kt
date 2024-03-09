package com.ajouin.noticescrapper.entity

import jakarta.persistence.*

@Entity
class LastIdPerType (

    var lastId: Long,

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    val type: Type,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)