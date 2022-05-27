import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
//    id("org.openjfx.javafxplugin") version "0.0.10"
}

group = "be.breina"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://mvnrepository.com/artifact/de.huxhorn.sulky/de.huxhorn.sulky.3rdparty.jlayer/1.0")
//    maven(url = "https://mvnrepository.com/artifact/javazoom/jlayer/1.0.1")
}

dependencies {

//    implementation("com.google.guava:guava:31.1-jre")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.languageVersion = "1.6"
    kotlinOptions.jvmTarget = "11"
}

//javafx {
//    modules("javafx.media")
//}