package ru.spanov.spring.webflux.tutorial.controller;

import static java.time.Duration.ofMillis;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  private final WebClient webClient = WebClient.create("http://localhost:8080");

  @GetMapping("/hello-world")
  public Mono<String> sayHello() {
    return Mono.just("Hello World!").delayElement(ofMillis(500));
  }

  @GetMapping("/call-sum")
  public Mono<String> callSum() {
    return webClient
        .get()
        .uri("/calculation/sum?a=1&b=2")
        .exchangeToMono(response -> {
          if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(String.class);
          } else if (response.statusCode().is4xxClientError()) {
            return Mono.just("Error response");
          } else {
            return response.createException()
                .flatMap(Mono::error);
          }
        });
  }

}
