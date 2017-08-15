package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Class describes types of server response.
 */
public enum ResponseCode {
    OK("OK"),
    INVALID_AMOUNT("Invalid amount. Please, set from 10 to 80"),
    INVALID_LINK("Invalid page link"),
    INTERNAL_ERROR("UNKNOWN ERROR");

    private @NotNull String description;

    ResponseCode(@NotNull String value) {
        this.description = value;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
