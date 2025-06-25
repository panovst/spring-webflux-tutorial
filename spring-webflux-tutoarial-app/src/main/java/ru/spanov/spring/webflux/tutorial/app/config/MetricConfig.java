package ru.spanov.spring.webflux.tutorial.app.config;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import java.time.Duration;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> enableHistogramForTimer() {
    return registry -> registry.config()
        .meterFilter(new MeterFilter() {
          @Override
          public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
            if (id.getName().startsWith("reactor.netty.http.client.response.time")) { // Target specific timer
              return DistributionStatisticConfig.builder()
                  .percentilesHistogram(true) // Enable histogram buckets based on percentiles
                  .serviceLevelObjectives(
                      Duration.ofNanos(200 * 1000000).toNanos()
                  ) // Define custom SLO buckets
                  .build()
                  .merge(config);
            }
            return config;
          }
        });
  }
}
