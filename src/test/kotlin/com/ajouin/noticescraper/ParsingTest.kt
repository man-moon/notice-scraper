package com.ajouin.noticescraper

import com.ajouin.noticescraper.service.ScrapingTask
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import org.assertj.core.api.Assertions.*
import org.jsoup.Jsoup
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

// 각 공지사항 별 mock data 파싱 테스트
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParsingTest @Autowired constructor(
    private val scrapingTask: ScrapingTask,
    private val strategies: List<ScrapingStrategy>,
) {
    val resourceDirectory = File("src/test/resources")

    fun strategyProvider() = strategies
        .mapNotNull { strategy ->
            strategyTestDataMap[strategy::class.simpleName]?.let { testData ->
                Arguments.of(strategy.noticeType.name, strategy, testData)
            }
        }
        .stream()

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategyProvider")
    @DisplayName("각 공지사항 파싱 테스트")
    fun parsingTestEachStrategies(
        noticeType: String,
        strategy: ScrapingStrategy,
        testData: StrategyTestData
    ) {
        val file = Paths.get("$resourceDirectory/${testData.htmlFileName}.html")
        val html = Files.readString(file)

        val doc = Jsoup.parse(html)

        val notices = scrapingTask.parseDocument(doc, strategy)
        val topFixedNotices = notices.filter { it.isTopFixed }

        assertThat(notices.size).isEqualTo(testData.expectedNoticesSize)
        assertThat(topFixedNotices.size).isEqualTo(testData.expectedTopFixedNoticesSize)

    }
}

data class StrategyTestData(
    val htmlFileName: String,
    val expectedNoticesSize: Int,
    val expectedTopFixedNoticesSize: Int
)

val strategyTestDataMap = mapOf(
    "GeneralNoticeStrategy" to StrategyTestData("GeneralNotice", 21, 11),
    "ScholarshipNoticeStrategy" to StrategyTestData("ScholarshipNotice", 19, 9),
    "DormitoryNoticeStrategy" to StrategyTestData("DormitoryNotice", 30, 20),
    "AppliedChemistryBiologicalEngineeringEmploymentNoticeStrategy" to StrategyTestData("AppliedChemistryBiologicalEngineeringEmploymentNotice", 11, 1),
    "AppliedChemistryBiologicalEngineeringNoticeStrategy" to StrategyTestData("AppliedChemistryBiologicalEngineeringNotice", 20, 10),
    "ConvergenceSystemsEngineering" to StrategyTestData("ConvergenceSystemsEngineeringNotice", 15, 5),
    "MathNoticeStrategy" to StrategyTestData("MathNotice", 10, 0),
    "MathEmploymentNoticeStrategy" to StrategyTestData("MathEmploymentNotice", 10, 0),
    "MathPartTimeNoticeStrategy" to StrategyTestData("MathPartTimeNotice", 10, 0),
    "ChemistryNoticeStrategy" to StrategyTestData("ChemistryNotice", 10, 0),
    "ChemistrySeminarNoticeStrategy" to StrategyTestData("ChemistrySeminarNotice", 10, 0),
    "BiologyNoticeStrategy" to StrategyTestData("BiologyNotice", 11, 1),
    "KoreanLanguageLiteratureNoticeStrategy" to StrategyTestData("KoreanLanguageLiteratureNotice", 21, 11),
    "EconomicsNoticeStrategy" to StrategyTestData("EconomicsNotice", 10, 0),
    "ArtificialIntelligenceConvergenceNoticeStrategy" to StrategyTestData("ArtificialIntelligenceConvergenceNotice", 11, 1),
    "CyberSecurityNoticeStrategy" to StrategyTestData("CyberSecurityNotice", 12, 2),
    "CyberSecurityEmploymentNoticeStrategy" to StrategyTestData("CyberSecurityEmploymentNotice", 10, 0),
    "DefenseDigitalConvergence" to StrategyTestData("DefenseDigitalConvergenceNotice", 16, 6),
    "MechanicalEngineeringNoticeStrategy" to StrategyTestData("MechanicalEngineeringNotice", 15, 5),
    "MechanicalCouncilNoticeStrategy" to StrategyTestData("MechanicalCouncilNotice", 10, 0),
    "MechanicalEmploymentNoticeStrategy" to StrategyTestData("MechanicalEmploymentNotice", 10, 0),
    "DigitalMediaNoticeStrategy" to StrategyTestData("DigitalMediaNotice", 16, 6),
    "DigitalMediaEmploymentNoticeStrategy" to StrategyTestData("DigitalMediaEmploymentNotice", 10, 0),
    "PhysicsNoticeStrategy" to StrategyTestData("PhysicsNotice", 11, 1),
    "PoliticalScienceDiplomacyNoticeStrategy" to StrategyTestData("PoliticalScienceDiplomacyNotice", 10, 0),
    "PublicAdministrationNoticeStrategy" to StrategyTestData("PublicAdministrationNotice", 12, 2),
    "PsychologyNoticeStrategy" to StrategyTestData("PsychologyNotice", 10, 0),
    "SociologyNoticeStrategy" to StrategyTestData("SociologyNotice", 10, 0),
    "SportsLeisureNoticeStrategy" to StrategyTestData("SportsLeisureNotice", 10, 0),
    "NursingNoticeStrategy" to StrategyTestData("NursingNotice", 10, 0),
    "MedicineNoticeStrategy" to StrategyTestData("MedicineNotice", 10, 0),
)