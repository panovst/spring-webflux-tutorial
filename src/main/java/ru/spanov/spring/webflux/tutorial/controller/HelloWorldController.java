package ru.spanov.spring.webflux.tutorial.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tutorial")
public class HelloWorldController {

  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

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

  @GetMapping("/thread-info-with-executor")
  public String threadInfoWithExecutor() {

    var future1 = executorService.submit(this::getThreadInfo);
    var future2 = executorService.submit(this::getThreadInfo);
    String threadInfo = null;
    try {
      threadInfo = future1.get() + "\n" + future2.get();
    } catch (InterruptedException | ExecutionException e) {
      log.error("Error:", e);
    }
    return threadInfo;
  }

  private String getThreadInfo() {
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
