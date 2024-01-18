package com.github.tacascer.predix

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test", "integration-test")
@SpringBootTest
class PredixApplicationTests {

    @Test
    fun contextLoads() {
    }

}
