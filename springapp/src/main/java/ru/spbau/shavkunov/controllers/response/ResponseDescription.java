package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Response description.
 */
public enum ResponseDescription {
    OK("OK"),
    INVALID_AMOUNT("Invalid amount. Please, set from 10 to 80"),
    INVALID_LINK("Invalid page link"),
    EMPTY_LINK("Please, enter link to vk user/community"),
    INTERNAL_ERROR("UNKNOWN ERROR");

    private @NotNull String description;
    private @Nullable ErrorType type;

    ResponseDescription(@NotNull String value) {
        this.description = value;

        // TODO : replace with more appropriate code.
        if (description.equals("Invalid amount. Please, set from 10 to 80")) {
            type = ErrorType.AMOUNT;
        }

        if (description.equals("Invalid page link")) {
            type = ErrorType.LINK;
        }

        type = null;
    }

    public ErrorType getType() {
        return type;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
