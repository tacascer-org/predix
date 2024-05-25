import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("com.adarshr.test-logger") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.jetbrains.kotlinx.kover") version "0.8.0"
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springframework.boot") version "3.2.5"
    kotlin("jvm") version "1.9.24"
    kotlin("kapt") version "1.9.24"
    kotlin("plugin.allopen") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "io.github.tacascer"
version = "1.4.0" // x-release-please-version

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.transaction.Transactional")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val mapstructVersion = "1.5.5.Final"
val instancioVersion = "4.6.0"
val kotestSpringVersion = "1.1.3"
val kotestVersion = "5.9.0"
val springMockkVersion = "4.0.2"
val springDocOpenApiVersion = "2.5.0"

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

kover {
    reports {
        filters {
            excludes {
                classes("io.github.tacascer.prediction.db.PredictionValueType") // Has JPA Buddy generated code
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.sonar {
    dependsOn(tasks.named("koverXmlReport"))
}

sonar {
    properties {
        property("sonar.projectKey", "tacascer-org_predix")
        property("sonar.organization", "tacascer-org")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.asFile.get()}/reports/kover/report.xml",
        )
    }
}

tasks.named<BootBuildImage>("bootBuildImage") {
    val image = "tacascer/${project.name}"
    imageName = image
    tags = listOf("$image:${project.version}", "$image:latest")
    if (System.getenv("DOCKER_HUB_TOKEN") != null) {
        publish = true
        docker {
            publishRegistry {
                username = "tacascer"
                password = System.getenv("DOCKER_HUB_TOKEN")
            }
        }
    }
}
