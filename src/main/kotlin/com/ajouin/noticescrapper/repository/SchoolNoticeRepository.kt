package com.ajouin.noticescrapper.repository

import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SchoolNoticeRepository: JpaRepository<SchoolNotice, Long> {
    fun findTopByOrderByIdDesc(): SchoolNotice?

    fun findAllByTypeAndIsTopFixedIsTrue(type: Type): List<SchoolNotice>
    fun findByFetchIdAndType(fetchId: Long, type: Type): SchoolNotice?
}