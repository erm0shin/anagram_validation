package service;

import org.example.service.ValidateAnagramCommandBuilder;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateAnagramCommandBuilderTest {
    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";

    private final ValidateAnagramCommandBuilder builder = new ValidateAnagramCommandBuilder();

    @Test
    void loadDefaultProperiesSuccessfully() {
        builder.init();
        final var command = builder.buildCommand(TEXT1, TEXT2);
        assertAll(
                () -> assertEquals(TEXT1, command.getText1()),
                () -> assertEquals(TEXT2, command.getText2()),
                () -> assertTrue(command.isIgnoreCase()),
                () -> assertEquals(Set.of(' ', ','), command.getSymbolsToIgnore())
        );
    }

    @Test
    void loadCustomProperiesSuccessfully() {
        builder.init("application.properties");
        final var command = builder.buildCommand(TEXT1, TEXT2);
        assertAll(
                () -> assertEquals(TEXT1, command.getText1()),
                () -> assertEquals(TEXT2, command.getText2()),
                () -> assertTrue(command.isIgnoreCase()),
                () -> assertEquals(Set.of(' ', ','), command.getSymbolsToIgnore())
        );
    }

    @Test
    void loadFallbackProperiesUnsucce() {
        builder.init("not_existing_file");
        final var command = builder.buildCommand(TEXT1, TEXT2);
        assertAll(
                () -> assertEquals(TEXT1, command.getText1()),
                () -> assertEquals(TEXT2, command.getText2()),
                () -> assertFalse(command.isIgnoreCase()),
                () -> assertEquals(Collections.emptySet(), command.getSymbolsToIgnore())
        );
    }
}
