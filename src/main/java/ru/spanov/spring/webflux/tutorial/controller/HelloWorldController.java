package ru.spanov.spring.webflux.tutorial.controller;

import java.util.concurrent.TimeUnit;
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
//    log.info("request received");
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      log.error("Error:", e);
    }
    var response = String.format("""
        thread: %s, \n
        cpu: %s
        """, Thread.currentThread(), Runtime.getRuntime().availableProcessors());
    return Mono.just(response);
  }

}
