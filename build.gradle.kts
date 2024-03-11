plugins {
    id("com.adarshr.test-logger") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"
    id("org.sonarqube") version "4.4.1.3373"
    id("org.springframework.boot") version "3.2.3"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
}

group = "com.github.tacascer"
version = "1.2.0" // x-release-please-version

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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

val instancioVersion = "4.4.0"
val kotestSpringVersion = "1.1.3"
val kotestVersion = "5.8.1"
val springMockkVersion = "4.0.2"
val springDocOpenApiVersion = "2.3.0"

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:$springDocOpenApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework:spring-jdbc")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.instancio:instancio-junit:$instancioVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:r2dbc")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
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
        property("sonar.projectKey", "tacascer_predix")
        property("sonar.organization", "tim-tran")
        property("sonar.host.url", "https://sonarcloud.io")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.asFile.get()}/reports/kover/report.xml"
        )
    }
}