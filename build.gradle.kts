import org.jetbrains.kotlin.config.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
    id("org.sonarqube") version "4.4.1.3373"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.github.tacascer"
version = "0.0.1-SNAPSHOT" // x-release-please-version

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
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
        property("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/kover/report.xml")
    }
}