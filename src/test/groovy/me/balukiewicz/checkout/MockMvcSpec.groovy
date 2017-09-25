package me.balukiewicz.checkout

import groovy.json.JsonBuilder
import me.balukiewicz.checkout.CheckoutApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CheckoutApplication])
class MockMvcSpec extends Specification {

    @Autowired
    protected WebApplicationContext context

    protected MockMvc mockMvc

    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

    String toJson(Object obj) {
        return new JsonBuilder(obj).toPrettyString()
    }

}