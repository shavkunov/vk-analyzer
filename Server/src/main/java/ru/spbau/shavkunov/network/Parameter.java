package ru.spbau.shavkunov.network;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public class Parameter {
    private @NotNull String name;
    private @NotNull String value;

    public Parameter(@NotNull String name, @NotNull String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
