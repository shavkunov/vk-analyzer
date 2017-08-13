package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;

import static ru.spbau.shavkunov.primitives.PostIdentity.BEST;

/**
 * Created by Mikhail Shavkunov
 */
public enum PostCategory {
    BEST_LIKED("The most liked post"),
    WORST_LIKED("The most not liked post"),
    BEST_VIEWS("The most viewed post"),
    WORST_VIEWS("The most not viewed post"),
    BEST_REPOSTS("The most reposted post"),
    WORST_REPOSTS("The most not reposted post");

    private @NotNull String value;

    // don't sure how to make it without duplicates
    public static PostCategory getCategory(@NotNull PostIdentity identity, @NotNull PostQuantity quantity) {
        if (identity == BEST) {
            switch (quantity) {
                case LIKES:
                    return BEST_LIKED;
                case REPOSTS:
                    return BEST_REPOSTS;
                case VIEWS:
                    return BEST_VIEWS;
            }
        }

        switch (quantity) {
            case LIKES:
                return WORST_LIKED;
            case REPOSTS:
                return WORST_REPOSTS;
            case VIEWS:
                return WORST_VIEWS;
        }

        // don't need
        return BEST_VIEWS;
    }

    PostCategory(@NotNull String value) {
        this.value = value;
    }

    @Override
    public @NotNull String toString() {
        return value;
    }
}
