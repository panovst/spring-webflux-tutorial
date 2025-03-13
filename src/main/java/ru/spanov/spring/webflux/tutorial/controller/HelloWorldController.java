package ru.spanov.spring.webflux.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  @GetMapping("/hello")
  public Mono<String> sayHello() {
    return Mono.just("Hello World!");
  }

  @GetMapping("/thread-info")
  public Mono<String> threadInfo() {
    return Mono.just(Thread.currentThread().toString());
  }

}
