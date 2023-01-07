package com.example.webfluxmongorsocket;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AircraftRepository extends ReactiveCrudRepository<Aircraft, String> {
}