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

    private static final @NotNull String okType = "OK";
    private static final @NotNull String errorType = "ERROR";

    private @NotNull String description;
    private @NotNull String type;

    ResponseCode(@NotNull String value) {
        this.description = value;

        if (description.equals(okType)) {
            type = okType;
        } else {
            type = errorType;
        }
    }

    public String getType() {
        return type;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
