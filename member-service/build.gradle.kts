dependencies {
        // Common Module
        implementation(project(":common"))

        // Spring Boot & Cloud
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-cache")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

        // Resilience4j
        implementation("io.github.resilience4j:resilience4j-spring-boot3")
        implementation("io.github.resilience4j:resilience4j-circuitbreaker")
        implementation("io.github.resilience4j:resilience4j-retry")
        implementation("io.github.resilience4j:resilience4j-timelimiter")
        implementation("io.github.resilience4j:resilience4j-feign")

        // Redis
        runtimeOnly("io.lettuce:lettuce-core")

        // JWT
        implementation("io.jsonwebtoken:jjwt-api")
        runtimeOnly("io.jsonwebtoken:jjwt-impl")
        runtimeOnly("io.jsonwebtoken:jjwt-jackson")

        // Kafka
        implementation("org.springframework.kafka:spring-kafka")

        // Database
        runtimeOnly("org.postgresql:postgresql")

        // Lombok
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        // Test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}