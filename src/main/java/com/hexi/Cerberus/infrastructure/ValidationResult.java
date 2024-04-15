package com.hexi.Cerberus.infrastructure;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ValidationResult {
    final List<String> problems;

    public List<String> getProblems() {
        return problems;
    }

    public boolean isValid() {
        return !hasErrors();
    }

    public boolean hasErrors() {
        return problems.size() != 0;
    }

    public void onFailedThrow() {
        if (hasErrors())
            throw new RuntimeException(problems.stream().reduce("Problems: \n    ", (s, s2) -> s + "\n    " + s2));

    }

    public void onFailedThrow(Function<List<String>, RuntimeException> throwable) {
        if (hasErrors()) throw throwable.apply(problems);

    }

}
