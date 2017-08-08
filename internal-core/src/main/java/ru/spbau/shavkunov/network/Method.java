package ru.spbau.shavkunov.network;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public enum Method {
    GROUP_GET_BY_ID("groups.getById"),
    USERS_GET("users.get"),
    WALL_GET("wall.get");

    private @NotNull String value;

    Method(@NotNull String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public String getValue() {
        return value;
    }
}
