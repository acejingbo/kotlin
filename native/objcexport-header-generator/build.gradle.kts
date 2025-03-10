@file:Suppress("HasPlatformType")

plugins {
    kotlin("jvm")
}

sourceSets {
    "main" { projectDefault() }
    "test" { projectDefault() }
}

dependencies {
    api(intellijCore())
    api(project(":native:base"))
    api(project(":core:compiler.common"))

    testApi(libs.junit.jupiter.api)
    testApi(libs.junit.jupiter.engine)
    testApi(libs.junit.jupiter.params)
    testApi(project(":compiler:tests-common", "tests-jar"))
}

kotlin {
    compilerOptions {
        optIn.add("org.jetbrains.kotlin.backend.konan.InternalKotlinNativeApi")
    }
}

/* Configure tests */

testsJar()

val k1TestRuntimeClasspath by configurations.creating
val analysisApiRuntimeClasspath by configurations.creating

dependencies {
    k1TestRuntimeClasspath(project(":native:objcexport-header-generator-k1"))
    k1TestRuntimeClasspath(projectTests(":native:objcexport-header-generator-k1"))

    analysisApiRuntimeClasspath(project(":native:objcexport-header-generator-analysis-api"))
    analysisApiRuntimeClasspath(projectTests(":native:objcexport-header-generator-analysis-api"))
}

tasks.test.configure {
    enabled = false
}

nativeTest("testK1", tag = null) {
    useJUnitPlatform()
    enableJunit5ExtensionsAutodetection()
    classpath += k1TestRuntimeClasspath
}

nativeTest("testAnalysisApi", tag = null) {
    useJUnitPlatform()
    enableJunit5ExtensionsAutodetection()
    testClassesDirs += files(sourceSets.test.map { it.output.classesDirs })
    classpath += analysisApiRuntimeClasspath
}

tasks.check.configure {
    dependsOn("testK1")
    dependsOn(":native:objcexport-header-generator-k1:check")
    dependsOn(":native:objcexport-header-generator-analysis-api:check")
}
