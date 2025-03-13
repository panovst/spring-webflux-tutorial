package ru.spanov.spring.webflux.tutorial.controller;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello World!";
  }

  @GetMapping("/thread-info")
  public String threadInfo() {
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      log.error("Error:", e);
    }
    return String.format("""
        thread: %s, \n
        cpu: %s
        """, Thread.currentThread(), Runtime.getRuntime().availableProcessors());
  }

}
