package com.example.webfluxmongorsocket;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PositionController {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    private final AircraftRepository aircraftRepository;

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

        Flux<Aircraft> dbData = aircraftRepository.findAll();

        model.addAttribute("currentPositions", dbData);
        return "positions";
    }

    @ResponseBody
    @GetMapping("/aircraftFluxJson")
    private Flux<Aircraft> getCurrentAircraftPositionsFluxJson() {
        //clear JPA repos all data
        aircraftRepository.deleteAll();
        Flux<Aircraft> aircraftFlux = client.get().retrieve().bodyToFlux(Aircraft.class);

        aircraftFlux.filter(plane -> !plane.getReg().isEmpty())
                        .flatMap(aircraftRepository::save);

        return aircraftRepository.findAll();
    }
}