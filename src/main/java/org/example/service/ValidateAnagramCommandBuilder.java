package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.ValidateAnagramCommand;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ValidateAnagramCommandBuilder {
    private static final String IGNORE_REGISTER_PROPERTY_KEY = "ignoreCase";
    private static final String SYMBOLS_TO_IGNORE_PROPERTY_KEY = "symbolsToIgnore";

    private volatile boolean propertiesLoaded = false;
    private volatile boolean ignoreCase = false;
    private volatile Set<Character> symbolsToIgnore = new HashSet<>();

    public void init() {
        init("application.properties");
    }

    public void init(final String propertyFileName) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (final var stream = loader.getResourceAsStream(propertyFileName)) {
            log.info("Loading properties from file configuration");
            final var properties = Arrays.stream(new String(stream.readAllBytes(), StandardCharsets.UTF_8).split("\n"))
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));

            ignoreCase = Boolean.parseBoolean(properties.get(IGNORE_REGISTER_PROPERTY_KEY));
            // Remove surrounding quotes
            properties.compute(SYMBOLS_TO_IGNORE_PROPERTY_KEY, (key, oldValue) -> oldValue.substring(1, oldValue.length() - 1));
            symbolsToIgnore = properties.get(SYMBOLS_TO_IGNORE_PROPERTY_KEY).chars()
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.toSet());
        } catch (final Exception e) {
            log.warn("Error occurred by loading properties from file", e);
            ignoreCase = false;
            symbolsToIgnore = new HashSet<>();
        }
        propertiesLoaded = true;
    }

    public ValidateAnagramCommand buildCommand(final String text1, final String text2) {
        // In case of using Spring, we don't need this flag
        // It would be sufficient to use @PostConstruct/@Value/@ConfigurationProperties to init config
        if (!propertiesLoaded) {
            init();
        }
        return ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .ignoreCase(ignoreCase)
                .symbolsToIgnore(symbolsToIgnore)
                .build();
    }
}
