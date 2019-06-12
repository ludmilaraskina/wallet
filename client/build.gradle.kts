import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val retrofitVersion = "2.5.0"
val okHttpVersion = "3.13.1"

plugins {
    kotlin("jvm")
    id(("org.springframework.boot")) version "2.1.5.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.31"
}

apply(plugin = "io.spring.dependency-management")
apply(plugin = "kotlin-spring")

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":wallet-api"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
    implementation("io.github.microutils:kotlin-logging:1.6.10")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("junit:junit")
}
