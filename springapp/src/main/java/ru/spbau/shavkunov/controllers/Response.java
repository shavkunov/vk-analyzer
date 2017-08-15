package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ru.spbau.shavkunov.controllers.ResponseDescription.OK;

/**
 * Created by Mikhail Shavkunov
 */
public class Response {
    private @NotNull ResponseType type;
    private @NotNull ResponseDescription description;
    private @Nullable Object data;

    public Response(@NotNull ResponseDescription description, @Nullable Object data) {
        this.description = description;
        if (description == OK) {
            type = ResponseType.OK;
        } else {
            type = ResponseType.ERROR;
        }

        this.data = data;
    }

    public ResponseType getType() {
        return type;
    }

    public ResponseDescription getDescription() {
        return description;
    }

    public Object getData() {
        return data;
    }
}