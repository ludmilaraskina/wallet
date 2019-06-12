import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id(("org.springframework.boot")) version "2.1.5.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.31"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.31"
}

apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-spring")
apply(plugin = "kotlin-noarg")
apply(plugin = "kotlin-jpa")


tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":wallet-api"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.flywaydb:flyway-core")
    implementation("io.github.microutils:kotlin-logging:1.6.10")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("junit:junit")
    testImplementation("com.h2database:h2")
}
