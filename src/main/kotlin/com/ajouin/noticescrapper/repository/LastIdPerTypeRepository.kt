package com.ajouin.noticescrapper.repository

import com.ajouin.noticescrapper.entity.LastIdPerType
import com.ajouin.noticescrapper.entity.Type
import org.springframework.data.jpa.repository.JpaRepository

interface LastIdPerTypeRepository: JpaRepository<LastIdPerType, Long> {
    fun findByType(type: Type): LastIdPerType?

}