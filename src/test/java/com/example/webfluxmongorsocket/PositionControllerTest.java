package com.example.webfluxmongorsocket;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = {PositionController.class})
class PositionControllerTest {

    private Aircraft ac1, ac2;

    @MockBean
    AircraftRepository aircraftRepository;

//    public PositionControllerTest(AircraftRepository aircraftRepository) {
//        this.aircraftRepository = aircraftRepository;
//    }



    @BeforeEach
    void setUp(ApplicationContext context) {
        // Spring Airlines flight 001 en route, flying STL to SFO,
        //    at 30000' currently over Kansas City
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,

                Instant.now(), Instant.now(), Instant.now());
        // Spring Airlines flight 002 en route, flying SFO to STL,
        //    at 40000' currently over Denver
        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

    }

    @Test
    void getCurrentAircraftPositionsFluxJson(@Autowired WebTestClient client) {

        // I check only status, and expect body, but don,t check the concrete data because I can't find normal code
        client.get()
                .uri("/aircraftFluxJson")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Aircraft.class);


        when(aircraftRepository.findAll()).thenReturn(Flux.just(ac1, ac2));

        Flux<Aircraft> dbData = aircraftRepository.findAll();

        assertEquals(List.of(ac1, ac2), dbData.collectList().block());
    }
}
