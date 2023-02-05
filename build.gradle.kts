plugins {
    `java-library`
    id("minestom.publishing-conventions")
    id("minestom.native-conventions")
    alias(libs.plugins.blossom)
}

allprojects {
    group = "net.minestom.server"
    version = "1.0-sakura"
    description = "Lightweight and multi-threaded Minecraft server implementation"
}

sourceSets {
    main {
        java {
            srcDir(file("src/autogenerated/java"))
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        getByName<MavenPublication>("maven") {
            repositories {
                maven(url = "https://maven.pkg.github.com/NxDs/Minestom")
            }

            groupId = "net.minestom.server"
            artifactId = "sakura"
            version = "${project.version}-${System.getenv("VERSION_SUFFIX")}"

            from(components["java"])
        }
    }
}

tasks {
    withType<Javadoc> {
        (options as? StandardJavadocDocletOptions)?.apply {
            encoding = "UTF-8"

            // Custom options
            addBooleanOption("html5", true)
            addStringOption("-release", "17")
            // Links to external javadocs
            links("https://docs.oracle.com/en/java/javase/17/docs/api/")
            links("https://jd.adventure.kyori.net/api/${libs.versions.adventure.get()}/")
        }
    }
    withType<Zip> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    blossom {
        val git = "src/main/java/net/minestom/server/Git.java"

        val gitCommit = System.getenv("GIT_COMMIT")
        val gitBranch = System.getenv("GIT_BRANCH")
        val group = System.getenv("GROUP")
        val artifact = System.getenv("ARTIFACT")

        replaceToken("\"&COMMIT\"", if (gitCommit == null) "null" else "\"${gitCommit}\"", git)
        replaceToken("\"&BRANCH\"", if (gitBranch == null) "null" else "\"${gitBranch}\"", git)
        replaceToken("\"&GROUP\"", if (group == null) "null" else "\"${group}\"", git)
        replaceToken("\"&ARTIFACT\"", if (artifact == null) "null" else "\"${artifact}\"", git)
    }

}

dependencies {
    // Testing Framework
    testImplementation(project(mapOf("path" to ":testing")))
    // Only here to ensure J9 module support for extensions and our classloaders
    testCompileOnly(libs.mockito.core)


    // Logging
    implementation(libs.bundles.logging)
    // Libraries required for the terminal
    implementation(libs.bundles.terminal)

    // Performance improving libraries
    implementation(libs.caffeine)
    api(libs.fastutil)
    implementation(libs.bundles.flare)

    // Libraries
    api(libs.gson)
    implementation(libs.jcTools)
    // Path finding
    api(libs.hydrazine)

    // Adventure, for user-interface
    api(libs.bundles.adventure)

    // Kotlin Libraries
    api(libs.bundles.kotlin)

    // Extension Management System dependency handler.
    api(libs.dependencyGetter)

    // Minestom Data (From MinestomDataGenerator)
    implementation(libs.minestomData)

    // NBT parsing/manipulation/saving
    api("io.github.jglrxavpok.hephaistos:common:${libs.versions.hephaistos.get()}")
    api("io.github.jglrxavpok.hephaistos:gson:${libs.versions.hephaistos.get()}")
}

