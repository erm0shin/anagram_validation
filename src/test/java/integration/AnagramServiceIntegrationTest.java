package integration;

import org.example.service.AnagramService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnagramServiceIntegrationTest {

    private final AnagramService anagramService = new AnagramService();

    @ParameterizedTest
    @MethodSource("provideAnagrams")
    void testDefaultAnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(text1, text2));
    }

    private static Stream<Arguments> provideAnagrams() {
        return Stream.of(
                Arguments.of("anagram", "nagaram"),
                Arguments.of("the detectives", "detect thieves"),
                Arguments.of("the Morse Code", "here come dots"),
                Arguments.of("conversation", "voices rant on"),
                Arguments.of("᠒ ᠕ ᠗", "᠒ ᠗ ᠕"),
                Arguments.of("Vacation time", "I am not active")
        );
    }

    @ParameterizedTest
    @MethodSource("provideNotAnagrams")
    void testNonAnagramLogic(final String text1, final String text2) {
        assertFalse(anagramService.isAnagram(text1, text2));
    }

    private static Stream<Arguments> provideNotAnagrams() {
        return Stream.of(
                Arguments.of("anagram", "not anagram"),
                Arguments.of("some words", "other words"),
                Arguments.of("Eleven plus one", "Ten plus two")
        );
    }

}
