package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public enum ErrorType {
    LINK("Link"),
    AMOUNT("Amount");

    private @NotNull String description;

    ErrorType(@NotNull String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
