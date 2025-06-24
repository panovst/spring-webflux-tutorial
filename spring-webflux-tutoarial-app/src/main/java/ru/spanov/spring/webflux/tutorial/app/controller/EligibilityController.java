package ru.spanov.spring.webflux.tutorial.app.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tutorial/eligibility")
public class EligibilityController {

  private final WebClient ossWebClient = WebClient.builder()
      .baseUrl("https://oss-prep-marfak-a-stage.apps.lmru.tech")
      .build();
  private final WebClient ormWebClient = WebClient.builder()
      .baseUrl("https://orm-prep-marfak-a-stage.apps.lmru.tech")
      .build();
  private final WebClient lopusWebClient = WebClient.builder()
      .baseUrl("http://10.203.39.196:8090")
      .build();

  @GetMapping("/byRegion")
  public Mono<String> callSum() {
    var request = Map.of(22124025, 47, 11740111, 2);
    var lopusRequest = lopusWebClient
        .post()
        .uri("/productEligibility/byRegion")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(filterRequestForLopus(request))
        .retrieve()
        .bodyToMono(String.class);
    var ossRequest = ossWebClient
        .post()
        .uri("/offer-items-zones-salability:search")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(filterRequestForOss(request))
        .retrieve()
        .bodyToMono(String.class);
    var ormRequest = ormWebClient
        .post()
        .uri("/best-offers")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(filterRequestForOrm(request))
        .retrieve()
        .bodyToMono(String.class);

    return Mono.zip(lopusRequest, ossRequest, ormRequest)
        .map(tuple -> "Lopus: " + tuple.getT1() + "\nOSS: " + tuple.getT2() + "\nORM: " + tuple.getT3());
  }

  private List<LopusRequestItem> filterRequestForLopus(Map<Integer, Integer> request) {
    return request.entrySet().stream()
        .map(entry -> new LopusRequestItem(entry.getKey(), entry.getValue()))
        .toList();
  }

  private List<BestOfferRequestItem> filterRequestForOrm(Map<Integer, Integer> request) {
    return request.entrySet().stream()
        .map(entry -> new BestOfferRequestItem(entry.getKey(), entry.getValue()))
        .toList();
  }

  private List<SalabilityRequestItem> filterRequestForOss(Map<Integer, Integer> request) {
    return request.entrySet().stream()
        .map(entry -> new SalabilityRequestItem(entry.getKey(), entry.getValue()))
        .toList();
  }

  private record SalabilityRequestItem(Integer itemId, Integer zoneId) {

  }

  private record BestOfferRequestItem(Integer itemId, Integer zoneId) {

  }

  private record LopusRequestItem(Integer productId, Integer regionId) {

  }
}
