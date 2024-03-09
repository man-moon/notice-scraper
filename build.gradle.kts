import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("kapt") version "1.9.22"
}

group = "com.ajouin"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.0"

dependencies {
	//spring
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	//spring cloud
	implementation("org.springframework.cloud:spring-cloud-starter-bus-amqp")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

	//monitoring
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("io.micrometer:micrometer-registry-prometheus")

	//jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	//QueryDsl
	implementation ("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

	//jsoup
	implementation("org.jsoup:jsoup:1.16.1")

	//slack
	implementation("com.slack.api:slack-api-client:1.32.1")

	//database
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	//logging
	implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

	//aws
	implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
//	implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
//	implementation("com.amazonaws:aws-lambda-java-events:3.11.2")
//	implementation("io.awspring.cloud:spring-cloud-aws-starter-parameter-store") : config import 용도
//	testImplementation("com.amazonaws:aws-java-sdk-core:1.12.481")

	implementation("org.springframework.boot:spring-boot-starter-web")


}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
