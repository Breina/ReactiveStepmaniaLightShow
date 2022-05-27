plugins {
    kotlin("jvm")
}

group = "be.breina"
version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation(project(":Common"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName("test", Test::useJUnitPlatform)

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.languageVersion = "1.6"
    kotlinOptions.jvmTarget = "11"
}