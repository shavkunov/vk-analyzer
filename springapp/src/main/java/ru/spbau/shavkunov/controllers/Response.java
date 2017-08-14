package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Class describes types of server response.
 */
public enum Response {
    OK("OK"),
    ERROR("ERROR");

    private @NotNull String value;

    Response(@NotNull String value) {
        this.value = value;
    }

    public @NotNull String getValue() {
        return value;
    }
}
