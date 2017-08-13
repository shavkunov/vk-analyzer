package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public enum PostQuantity {
    VIEWS("views"),
    LIKES("likes"),
    REPOSTS("reposts");

    private @NotNull String value;

    PostQuantity(@NotNull String value) {
        this.value = value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
