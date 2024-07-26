package com.masri.TaskMangamentSystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing the Jackson ObjectMapper in the application.
 * @author Ahmad Al-Masri
 *
 */
@Configuration
public class JacksonConfig {

    /**
     * Provides a custom {@link ObjectMapper} bean that registers the
     * {@link JavaTimeModule} to support Java 8 Date and Time API types.
     *
     * @return a configured {@link ObjectMapper} instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Registering JavaTimeModule to handle Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}
