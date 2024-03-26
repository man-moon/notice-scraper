package com.ajouin.noticescraper.config

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.entity.NoticeType.*
import com.ajouin.noticescraper.parser.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ParserConfiguration {

    @Bean
    fun parserFactory(): ParserFactory {
        val parserMap = mapOf(
            // 포탈 메인
            일반공지 to NewMainParser,
            장학공지 to NewMainParser,
            생활관 to NewMainParser,

            // 단과대
            경영대학 to NewMainParser,
            공과대학 to NewMainParser,
            인문대학 to NewMainParser,
            정보통신대학 to NewMainParser,
            자연과학대학 to NewMainParser,
            의과대학 to MedicalDepParser,
            간호대학 to NursingDepParser,
            약학대학 to NewMainParser,
            사회과학대학 to NewMainParser,
            소프트웨어융합대학 to NewMainParser,

            // 학과
            응용화학생명공학과0 to NewMainParser,
            응용화학생명공학과1 to NewSubParser,
            건축학과0 to OldMainParser,
            건축학과1 to OldSubParser,
            건축학과2 to OldSubParser,
            건축학과3 to OldSubParser,
            화학공학과0 to NewMainParser,
            화학공학과1 to NewSubBN02Parser,
            화학과0 to NewMainParser,
            화학과1 to NewSubParser,
            사이버보안학과0 to NewSubParser,
            사이버보안학과1 to NewSubParser,
            디지털미디어학과0 to NewMainParser,
            디지털미디어학과1 to NewSubParser,
            전자공학과0 to NewMainParser,
            전자공학과1 to NewSubBN02Parser,
            산업공학과0 to NewMainParser,
            산업공학과1 to NewSubParser,
            산업공학과2 to NewSubParser,
            수학과0 to NewMainParser,
            수학과1 to NewSubParser,
            수학과2 to NewSubParser,
            기계공학과0 to NewMainParser,
            기계공학과1 to NewSubParser,
            기계공학과2 to NewSubParser,
            소프트웨어학과0 to SoftwareDepParser,
            소프트웨어학과1 to SoftwareDepParser,
            첨단신소재공학과 to NewMainParser,
            AI모빌리티공학과 to NewSubBN02CateParser,
            인공지능융합학과 to NewMainParser,
            생명과학과 to NewMainParser,
            경영학과 to NewMainParser,
            건설시스템공학과 to NewMainParser,
            융합시스템공학과 to NewSubBN02Parser,
            문화콘텐츠학과 to NewMainParser,
            국방디지털융합학과 to NewMainParser,
            e비즈니스학과 to NewMainParser,
            경제학과 to NewMainParser,
            영어영문학과 to NewMainParser,
            환경안전공학과 to NewMainParser,
            금융공학과 to NewMainParser,
            불어불문학과 to NewMainParser,
            글로벌경영학과 to NewMainParser,
            사학과 to NewMainParser,
            지능형반도체공학과 to NewSubBN01CateParser,
            국어국문학과 to NewMainParser,
            물리학과 to NewMainParser,
            정치외교학과 to NewMainParser,
            심리학과 to NewMainParser,
            행정학과 to NewMainParser,
            사회학과 to NewMainParser,
            스포츠레저학과 to NewMainParser,
            교통시스템공학과 to NewMainParser,
        )

        return ParserFactory(parserMap)
    }
}