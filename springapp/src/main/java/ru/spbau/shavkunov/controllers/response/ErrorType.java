package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public enum ErrorType {
    LINK("LINK"),
    AMOUNT("AMOUNT");

    private @NotNull String description;

    ErrorType(@NotNull String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
