package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Mikhail Shavkunov
 */
public class Response {
    private @NotNull ResponseType type;
    private @NotNull ResponseDescription description;
    private @Nullable Object data;

    public Response(@NotNull String description, @Nullable Object data) {
        this.description = new ResponseDescription(description);
        if (description.equals(ResponseDescription.OK)) {
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