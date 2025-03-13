package ru.spanov.spring.webflux.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

  @GetMapping
  public Mono<String> sayHello() {
    return Mono.just("Hello World!");
  }

}
