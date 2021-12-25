import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("fabric-loom")
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm").version(kotlinVersion)

    id("com.github.johnrengelman.shadow") version "7.0.0"
}
base {
    val archivesBaseName: String by project
    archivesName.set(archivesBaseName)
}
val modVersion: String by project
version = modVersion

val mavenGroup: String by project
group = mavenGroup

minecraft {}

repositories {
    maven("https://maven.nucleoid.xyz")
    mavenCentral()
}

dependencies {
    val minecraftVersion: String by project
    minecraft("com.mojang:minecraft:$minecraftVersion")

    val yarnMappings: String by project
    mappings("net.fabricmc:yarn:$yarnMappings:v2")

    val loaderVersion: String by project
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    val fabricVersion: String by project
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")

    val fabricKotlinVersion: String by project
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")

    val polymerVersion: String by project
    modApi("eu.pb4:polymer:$polymerVersion") {
        exclude("net.fabricmc.fabric-api")
    }

    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}
tasks {
    val javaVersion = JavaVersion.VERSION_16
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }
    withType<KotlinCompile> {
        kotlinOptions { jvmTarget = javaVersion.toString() }
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
    }
    jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") { expand(mutableMapOf("version" to project.version)) }
    }
    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(javaVersion.toString())) }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    dependencies {
    }
}