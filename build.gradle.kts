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
    implementation(group = "de.huxhorn.sulky", name = "de.huxhorn.sulky.3rdparty.jlayer", version = "1.0")
    implementation("io.gitlab.mguimard:openrgb-client:1.15")
    testImplementation(kotlin("test"))
    implementation("ch.bildspur:artnet4j:0.6.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}

//javafx {
//    modules("javafx.media")
//}