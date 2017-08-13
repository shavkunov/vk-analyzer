package ru.spbau.shavkunov.primitives;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.users.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Post {
    @Id
    private @Nullable String id;

    // save some other statistics to show user
    private @NotNull String text;
    private @Nullable String defaultImage;
    private @NotNull User owner;
    private @NotNull String description;

    @ElementCollection(targetClass=String.class)
    private @Nullable List<String> images;

    public Post(@NotNull User owner, @NotNull Map json, @NotNull PostCategory category) {
        text = (String) json.get("text");

        if (countPhotoAttachments(json) == 0) {
            defaultImage = owner.getPhoto().toString();
            return;
        }

        this.owner = owner;
        description = category.toString();
        images = getAttachedImageURLs(json);
    }

    private @NotNull List<String> getAttachedImageURLs(@NotNull Map json) {
        List<Map> attach = (List) json.get("attachments");

        return attach.stream()
                // TODO : handle photo request
                .filter(map -> map.get("type").equals("photo"))
                .map(map -> (Map) map.get("photo"))
                .map(map -> (String) map.get("photo_130"))
                .collect(Collectors.toList());
    }

    private long countPhotoAttachments(@NotNull Map json) {
        List<Map> attach = (List) json.get("attachments");
        return attach.stream()
                     .filter(map -> map.get("type").equals("photo"))
                     .count();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public List<String> getImages() {
        return images;
    }
}
