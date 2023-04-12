package org.example.service;

import org.example.model.ValidateAnagramCommand;

import java.util.HashMap;
import java.util.Map;

public class AnagramService {

    private final ValidateAnagramCommandBuilder commandBuilder = new ValidateAnagramCommandBuilder();

    public boolean isAnagram(final String text1, final String text2) {
        commandBuilder.buildCommand(text1, text2);
        final var command = commandBuilder.buildCommand(text1, text2);
        return isAnagram(command);
    }

    public boolean isAnagram(final ValidateAnagramCommand command) {
        final Map<Character, Integer> charToCountMap = new HashMap<>();
        countSymbols(command.getText1(), charToCountMap, 1, command);
        countSymbols(command.getText2(), charToCountMap, -1, command);
        return validateCounters(charToCountMap);
    }

    private void countSymbols(
            final String str,
            final Map<Character, Integer> charToCountMap,
            final int increment,
            final ValidateAnagramCommand command
    ) {
        for (int i = 0; i < str.length(); i++) {
            final char curSymbol = command.isIgnoreRegister() ? Character.toLowerCase(str.charAt(i)) : str.charAt(i);
            if (!command.getSymbolsToIgnore().contains(curSymbol)) {
                charToCountMap.merge(curSymbol, increment, Integer::sum);
            }
        }
    }

    private boolean validateCounters(final Map<Character, Integer> charToCountMap) {
        return charToCountMap.values().stream()
                .allMatch(counter -> counter == 0);
    }

}