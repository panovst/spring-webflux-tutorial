package ru.spanov.spring.webflux.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello World!";
  }

  @GetMapping("/thread-info")
  public String threadInfo() {
    return Thread.currentThread().toString();
  }

}
