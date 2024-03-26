package com.ajouin.noticescraper.repository

import com.ajouin.noticescraper.entity.QSchoolNotice
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SchoolNoticeQuerydslRepository(
    val queryFactory: JPAQueryFactory
) {
    fun findByTypeContaining(noticeTypes: List<NoticeType>): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 동적 쿼리 생성
        return queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(noticeTypes))
            .fetch()
    }

    //상단 고정 공지는 항상 모두 가져오고, 상단 고정 공지가 아닌 공지사항을 fetchId가 큰 순으로 10개씩 가져오기(페이징 처리)
    fun findNoticesByPaging(
        offset: Long,
        limit: Long,
        noticeTypes: List<NoticeType>,
        includeTopFixed: Boolean
    ): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 상단 고정 공지를 모두 가져옵니다.
        val topFixedNotices = if(includeTopFixed) {
            queryFactory
                .selectFrom(qSchoolNotice)
                .where(typeIn(noticeTypes))
                .where(qSchoolNotice.isTopFixed.eq(true))
                .orderBy(qSchoolNotice.fetchId.desc())
                .fetch()
        } else {
            emptyList()
        }

        // 상단 고정이 아닌 공지를 페이징 처리하여 가져옵니다.
        val generalNotices = queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(noticeTypes))
            .where(qSchoolNotice.isTopFixed.eq(false))
            .orderBy(qSchoolNotice.fetchId.desc())
            .offset(offset)
            .limit(limit)
            .fetch()

        return topFixedNotices + generalNotices
    }

    fun findNoticesByPagingForSoftwareDep(
        offset: Long,
        limit: Long,
        noticeTypes: List<NoticeType>,
        includeTopFixed: Boolean
    ): List<SchoolNotice> {
        val qSchoolNotice = QSchoolNotice.schoolNotice

        // 상단 고정 공지를 모두 가져옵니다.
        val topFixedNotices = if(includeTopFixed) {
            queryFactory
                .selectFrom(qSchoolNotice)
                .where(typeIn(noticeTypes))
                .where(qSchoolNotice.isTopFixed.eq(true))
                .orderBy(qSchoolNotice.fetchId.desc())
                .fetch()
        } else {
            emptyList()
        }

        // 상단 고정이 아닌 공지를 페이징 처리하여 가져옵니다.
        val generalNotices = queryFactory
            .selectFrom(qSchoolNotice)
            .where(typeIn(noticeTypes))
            .where(qSchoolNotice.isTopFixed.eq(false))
            .orderBy(qSchoolNotice.date.desc())
            .offset(offset)
            .limit(limit)
            .fetch()

        return topFixedNotices + generalNotices
    }

    private fun typeIn(noticeTypes: List<NoticeType>): BooleanExpression? {
        val qSchoolNotice = QSchoolNotice.schoolNotice
        return noticeTypes.map { qSchoolNotice.noticeType.eq(it) }
            .reduce(BooleanExpression::or)
    }
}