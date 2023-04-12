package org.example.model;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class ValidateAnagramCommand {
    private final String text1;
    private final String text2;
    @Builder.Default
    private final boolean ignoreRegister = false;
    @Builder.Default
    private final Set<Character> symbolsToIgnore = new HashSet<>();
}
