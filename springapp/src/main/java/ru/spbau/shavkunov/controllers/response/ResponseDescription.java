package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;

/**
 * Response description.
 */
public class ResponseDescription {
    public static final @NotNull String OK = "OK";
    public static final @NotNull String INVALID_AMOUNT = "Invalid amount. Please, set from 10 to 80";
    public static final @NotNull String INVALID_LINK = "Invalid page link";
    public static final @NotNull String EMPTY_LINK = "Please, enter link to vk user/community";
    public static final @NotNull String INTERNAL_ERROR = "UNKNOWN ERROR";

    private @NotNull String description;
    private @NotNull ErrorType type;

    public ResponseDescription(@NotNull String value) {
        this.description = value;

        // TODO : replace with more appropriate code.
        if (description.equals(INVALID_AMOUNT)) {
            type = ErrorType.AMOUNT;
        }

        if (description.equals(INVALID_LINK)) {
            type = ErrorType.LINK;
        }

        if (description.equals(EMPTY_LINK)) {
            type = ErrorType.LINK;
        }
    }

    ResponseDescription() {
    }

    public ErrorType getType() {
        return type;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
