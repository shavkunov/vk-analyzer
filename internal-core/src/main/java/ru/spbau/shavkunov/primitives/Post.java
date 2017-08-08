package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.users.User;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Post {
    private @Nullable String id;
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
