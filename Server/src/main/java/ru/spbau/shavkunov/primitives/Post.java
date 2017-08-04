package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.Users.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Mikhail Shavkunov
 */
public class Post {
    private @NotNull String text;
    private @Nullable String defaultImage;
    private @Nullable List<String> images;

    public Post(@NotNull User owner, @NotNull Map json) {
        text = (String) json.get("text");

        if (countPhotoAttachments(json) == 0) {
            defaultImage = owner.getPhoto().toString();
            return;
        }

        images = getAttachedImageURLs(json);
    }

    private @NotNull List<String> getAttachedImageURLs(@NotNull Map json) {
        Map<String, Map> attach = (Map<String, Map>) json.get("attachments");
        return attach.entrySet()
                .stream()
                .filter(entry -> entry.getValue()
                        .get("type") == "photo")
                .map(entry -> entry.getValue())
                .map(map -> (String) map.get("src"))
                .collect(Collectors.toList());
    }

    private long countPhotoAttachments(@NotNull Map json) {
        Map<String, Map> attach = (Map<String, Map>) json.get("attachments");
        return attach.entrySet()
                     .stream()
                     .filter(entry -> entry.getValue()
                                           .get("type") == "photo")
                     .count();
    }
}
