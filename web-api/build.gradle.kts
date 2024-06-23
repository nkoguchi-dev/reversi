import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    application
}

group = "org.koppepan.reversi"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // r2dbc + h2
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("io.r2dbc:r2dbc-h2")

    // Flyway
    runtimeOnly("org.flywaydb:flyway-core")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // Komapper
    val komapperVersion = "1.17.0"
    platform("org.komapper:komapper-platform:$komapperVersion").let {
        implementation(it)
        ksp(it)
    }
    implementation("org.komapper:komapper-starter-r2dbc")
    implementation("org.komapper:komapper-dialect-h2-r2dbc")
    ksp("org.komapper:komapper-processor")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    testImplementation("io.projectreactor:reactor-test")
}


buildscript {
    repositories {
        mavenCentral()
    }
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/kotlin", "src/main/resources")
        }
    }
    create("integrationTest") {
        compileClasspath += sourceSets["main"].output
        runtimeClasspath += sourceSets["main"].output
        kotlin.srcDir("src/integrationTest/kotlin")
        resources.srcDir("src/integrationTest/resources")
    }
}

configurations {
    getByName("integrationTestImplementation") {
        extendsFrom(configurations.getByName("testImplementation"))
    }
    getByName("integrationTestRuntimeOnly") {
        extendsFrom(configurations.getByName("testRuntimeOnly"))
    }
}

sonar {
    properties {
        property("sonar.projectKey", System.getenv("REVERSI_WEB_API_SONAR_PROJECT_KEY") ?: "")
        property("sonar.projectName", System.getenv("REVERSI_WEB_API_SONAR_PROJECT_NAME") ?: "")
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory}/jacoco-xml-report/test.xml"
        )
        property("sonar.host.url", System.getenv("REVERSI_WEB_API_SONAR_HOST_URL") ?: "")
        property("sonar.token", System.getenv("REVERSI_WEB_API_SONAR_TOKEN") ?: "")
    }
}

jacoco {
    toolVersion = "0.8.9"
    reportsDirectory.set(layout.buildDirectory.dir("jacoco-xml-report"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    executionData.setFrom(
        fileTree(
            mapOf(
                "dir" to layout.buildDirectory.dir("jacoco"),
                "includes" to listOf("test.exec", "integrationTest.exec"),
            )
        )
    )
    sourceDirectories.setFrom(sourceSets.main.get().allSource.srcDirs)

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(false)
        xml.outputLocation.set(file("${layout.buildDirectory}/jacoco-xml-report/test.xml"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Copy>("processIntegrationTestResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Test>("integrationTest") {
    group = "verification"
    description = "Runs integration tests."
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    systemProperty(
        "spring.config.location",
        "classpath:/application.yml,classpath:/application-integration-test.yml"
    )
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.bootRun {
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}

tasks.withType<Jar> {
    archiveFileName.set("reversi_web-api.jar")
}

application {
    mainClass.set("org.koppepan.reversi.webapi.WebApiApplication")
}

tasks {
    named<Jar>("jar") {
        manifest {
            attributes["Main-Class"] = "org.koppepan.reversi.webapi.WebApiApplication"
        }
    }

    named<Zip>("bootDistZip") {
        dependsOn("jar")
    }

    named<Tar>("bootDistTar") {
        dependsOn("jar")
    }

    named<CreateStartScripts>("bootStartScripts") {
        dependsOn("jar")
    }
}