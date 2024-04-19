import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.flywaydb.flyway") version "9.8.1"
    kotlin("jvm") version "2.0.0-RC1"
    kotlin("plugin.spring") version "2.0.0-RC1"
}

group = "org.koppepan.demo"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // PostgreSQL + flyway + mybatis
	implementation("org.postgresql:postgresql")
	runtimeOnly("org.flywaydb:flyway-core")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // テスト
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("org.mockito:mockito-inline:5.1.0")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.2")
}

tasks.register<Test>("integrationTest") {
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    useJUnitPlatform()
    systemProperty("spring.config.location", "classpath:/application.yml,classpath:/application-integration-test.yml")
}

tasks.named("compileKotlin", KotlinCompilationTask::class) {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

flyway {
    schemas = arrayOf("demo")
    url = "jdbc:postgresql://localhost:15432/demodb"
    user = "pgadmin"
    password = "pgadmin"
    flyway.cleanDisabled = true
}
