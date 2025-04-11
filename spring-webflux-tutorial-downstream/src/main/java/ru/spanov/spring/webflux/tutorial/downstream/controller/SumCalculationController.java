package ru.spanov.spring.webflux.tutorial.downstream.controller;

import static java.time.Duration.ofMillis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/calculation")
public class SumCalculationController {

  @GetMapping("/sum")
  public Mono<Integer> sum(@RequestParam int a, @RequestParam int b) {
    return Mono.just(a + b)
        .delayElement(ofMillis(500))
//        .log()
        ;
  }

}
