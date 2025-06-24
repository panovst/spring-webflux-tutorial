package ru.spanov.spring.webflux.tutorial.app.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tutorial/eligibility")
public class EligibilityController {

  private final WebClient ormWebClient = WebClient.builder()
      .baseUrl("https://orm-prep-marfak-a-stage.apps.lmru.tech")
      .build();

  @GetMapping("/byRegion")
  public Mono<String> callSum() {
//    return Mono.just(String.valueOf(1 + 2)).delayElement(ofMillis(500));
    return ormWebClient
        .post()
        .uri("/best-offers")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(List.of(
            new BestOfferRequestItem(22124025, 47),
            new BestOfferRequestItem(11740111, 2)
        ))
        .retrieve()
        .bodyToMono(String.class);
  }

  private record BestOfferRequestItem (Integer itemId, Integer zoneId) {

  }
}
