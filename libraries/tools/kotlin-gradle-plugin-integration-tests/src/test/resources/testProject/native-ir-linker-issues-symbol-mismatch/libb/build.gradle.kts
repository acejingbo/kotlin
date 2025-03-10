plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
}

group = "org.sample"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("<localRepo>")
}

kotlin {
    <SingleNativeTarget>("native")

    sourceSets["nativeMain"].dependencies {
        implementation("org.sample:liba:1.0")
    }
}

publishing {
    repositories {
        maven {
            url = uri("<localRepo>")
        }
    }
}
