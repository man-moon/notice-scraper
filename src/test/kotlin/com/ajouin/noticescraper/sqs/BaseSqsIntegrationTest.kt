package com.ajouin.noticescraper.sqs

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest
@Testcontainers
class BaseSqsIntegrationTest {
    companion object {
        private const val LOCAL_STACK_VERSION = "localstack/localstack:2.3.2"

        @Container
        @JvmField
        val localStack: LocalStackContainer =
            LocalStackContainer(DockerImageName.parse(LOCAL_STACK_VERSION))

        @DynamicPropertySource
        @JvmStatic
        fun overrideProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.cloud.aws.region.static") { localStack.region }
//            registry.add("spring.cloud.aws.credentials.access-key") { localStack.accessKey }
//            registry.add("spring.cloud.aws.credentials.secret-key") { localStack.secretKey }
//            registry.add("spring.cloud.aws.sqs.endpoint") { localStack.getEndpointOverride(SQS).toString() }
        }
    }

}