package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Response description.
 */
public enum ResponseDescription {
    OK("OK"),
    INVALID_AMOUNT("Invalid amount. Please, set from 10 to 80"),
    INVALID_LINK("Invalid page link"),
    INTERNAL_ERROR("UNKNOWN ERROR");

    private @NotNull String description;

    ResponseDescription(@NotNull String value) {
        this.description = value;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
