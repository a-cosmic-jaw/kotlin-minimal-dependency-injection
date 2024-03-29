plugins {
    id("org.jetbrains.kotlin.jvm")
    idea
    application
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
}

val kotlinVersion: String by properties
val javaVersion: String by properties

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

}

kotlin {
    jvmToolchain(Integer.parseInt(javaVersion))
}

application {
    mainClass.set("MainKt")
}