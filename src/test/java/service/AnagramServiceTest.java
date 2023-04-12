package service;

import org.example.model.ValidateAnagramCommand;
import org.example.service.AnagramService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnagramServiceTest {

    private final AnagramService anagramService = new AnagramService();

    @ParameterizedTest
    @MethodSource("provideSimpleAnagrams")
    void testDefaultAnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .build()));
    }

    private static Stream<Arguments> provideSimpleAnagrams() {
        return Stream.of(
                Arguments.of("anagram", "nagaram"),
                Arguments.of("bored", "robed"),
                Arguments.of("the detectives", "detect thieves")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRegisteredAnagrams")
    void testRegisteredAnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .ignoreRegister(true)
                .build()));
    }

    private static Stream<Arguments> provideRegisteredAnagrams() {
        return Stream.of(
                Arguments.of("Elvis Presley", "Presley lives"),
                Arguments.of("the Morse Code", "here come dots"),
                Arguments.of("Russell Crowe", "scowler rules")
        );
    }

    @ParameterizedTest
    @MethodSource("provideSpacedAnagrams")
    void testSpacedAnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .symbolsToIgnore(Set.of(' '))
                .build()));
    }

    private static Stream<Arguments> provideSpacedAnagrams() {
        return Stream.of(
                Arguments.of("astronomer", "moon starer"),
                Arguments.of("dormitory", "dirty room"),
                Arguments.of("conversation", "voices rant on")
        );
    }

    @ParameterizedTest
    @MethodSource("provideUTF16Anagrams")
    void testUTF16AnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .build()));
    }

    private static Stream<Arguments> provideUTF16Anagrams() {
        return Stream.of(
                Arguments.of("ᠠ", "ᠠ"),
                Arguments.of("ൟൠൡ", "ൡൟൠ"),
                Arguments.of("᠒ ᠕ ᠗", "᠒ ᠗ ᠕")
        );
    }

    @ParameterizedTest
    @MethodSource("provideComplexAnagrams")
    void testComplexAnagramLogic(final String text1, final String text2) {
        assertTrue(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .ignoreRegister(true)
                .symbolsToIgnore(Set.of(' '))
                .build()));
    }

    private static Stream<Arguments> provideComplexAnagrams() {
        return Stream.of(
                Arguments.of("Tom Marvolo Riddle", "I am Lord Voldemort"),
                Arguments.of("Vacation time", "I am not active"),
                Arguments.of("For the evil that men do", "doth live on after them")
        );
    }

    @ParameterizedTest
    @MethodSource("provideNonAnagrams")
    void testNonAnagramLogic(final String text1, final String text2) {
        assertFalse(anagramService.isAnagram(ValidateAnagramCommand.builder()
                .text1(text1)
                .text2(text2)
                .ignoreRegister(true)
                .symbolsToIgnore(Set.of(' '))
                .build()));
    }

    private static Stream<Arguments> provideNonAnagrams() {
        return Stream.of(
                Arguments.of("anagram", "not anagram"),
                Arguments.of("some words", "other words"),
                Arguments.of("Eleven plus one", "Ten plus two")
        );
    }

}
