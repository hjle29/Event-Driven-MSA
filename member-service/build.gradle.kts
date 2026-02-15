dependencies {
        implementation(project(":common"))

        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

        implementation("io.github.resilience4j:resilience4j-spring-boot3")
        implementation("io.github.resilience4j:resilience4j-circuitbreaker")
        implementation("io.github.resilience4j:resilience4j-retry")
        implementation("io.github.resilience4j:resilience4j-timelimiter")
        implementation("io.github.resilience4j:resilience4j-feign")

        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-cache")
        runtimeOnly("io.lettuce:lettuce-core")

        runtimeOnly("org.postgresql:postgresql")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}