package ru.spanov.spring.webflux.tutorial.app.controller;

import static java.time.Duration.ofMillis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  private final WebClient localhostWebClient = WebClient.builder()
      .baseUrl("http://localhost:8080")
      .build();

  private final WebClient ossWebClient = WebClient.builder()
      .baseUrl("https://oisia-dev-marfak-a-stage.apps.lmru.tech")
      .build();

  @GetMapping("/hello-world")
  public Mono<String> sayHello() {
    return Mono.just("Hello World!").delayElement(ofMillis(500));
  }

  @GetMapping("/call-sum")
  public Mono<String> callSum() {
//    return Mono.just(String.valueOf(1 + 2)).delayElement(ofMillis(500));
    return localhostWebClient
        .get()
        .uri("/calculation/sum?a=1&b=2")
        .retrieve()
        .bodyToMono(String.class);
  }

  @GetMapping("/call-oss")
  public Mono<String> callOss() {
//    return Mono.just(String.valueOf(1 + 2)).delayElement(ofMillis(500));
    return ossWebClient
        .get()
        .uri("/actuator/health")
        .retrieve()
        .bodyToMono(String.class);
  }

}
