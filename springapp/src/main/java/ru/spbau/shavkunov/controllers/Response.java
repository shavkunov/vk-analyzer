package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Mikhail Shavkunov
 */
public class Response {
    private @NotNull ResponseCode responseCode;
    private @Nullable Object data;

    public Response(@NotNull ResponseCode responseCode, @Nullable Object data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public Object getData() {
        return data;
    }
}
