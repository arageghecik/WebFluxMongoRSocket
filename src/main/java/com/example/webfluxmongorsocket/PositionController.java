package com.example.webfluxmongorsocket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Controller
//@RequiredArgsConstructor
public class PositionController {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final AircraftRepository aircraftRepository;
    private final RSocketRequester requester;

    public PositionController(AircraftRepository repository, RSocketRequester.Builder builder) {
        this.aircraftRepository = repository;
        this.requester = builder.tcp("localhost", 7635);
    }

    @GetMapping("/aircraft")
    private String getCurrentAircraftPositions(Model model) {
        //clear JPA repos all data
        aircraftRepository.deleteAll();
        //flux reactive data type
        //flux is publisher in reactive approach, we get data from webclient in flux form
        Flux<Aircraft> aircraftFlux = client.get().retrieve().bodyToFlux(Aircraft.class);

        //storing data to JPA repo
        aircraftFlux.filter(plane -> !plane.getReg().isEmpty())
                //.toStream().forEach(aircraftRepository::save);
                        .flatMap(aircraftRepository::save);

        model.addAttribute("currentPositions", aircraftFlux);
        return "positions";
    }
    // HTTP endpoint, RSocket client endpoint
    @ResponseBody
    @GetMapping(value = "/acstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Aircraft> getCurrentACPositionsStream() {
        return requester.route("acstream")
                .data("Requesting aircraft positions")
                .retrieveFlux(Aircraft.class);
    }


}