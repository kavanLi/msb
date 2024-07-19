package com.example.demo;

import com.example.demo.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:44
 * To change this template use File | Settings | File and Code Templates.
 */
@WebFluxTest(TestController.class)
public class WebFluxControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testEndpoint() {
        EntityExchangeResult<byte[]> result = webClient.post()
                .uri("/test/foobar")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody().returnResult();

        String responseBody = new String(result.getResponseBody());

        System.out.println("Response Body: " + responseBody);
    }
}