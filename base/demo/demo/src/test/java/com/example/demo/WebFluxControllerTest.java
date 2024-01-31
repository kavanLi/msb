package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class WebFluxControllerTest {


    private final WebTestClient webTestClient = WebTestClient.bindToServer().build();

    @Test
    public void testUser1() {
        webTestClient.get().uri("http://127.0.0.1:8081/api/user1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Hello, WebFlux !");
    }

    @Test
    public void getNumbers() {
        List <Integer> expectedNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        webTestClient.get().uri("http://127.0.0.1:8081/api/numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_STREAM_JSON)
                .expectBodyList(Integer.class)
                .isEqualTo(expectedNumbers);
    }
}
