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

import static ru.spbau.shavkunov.primitives.PostQuantity.LIKES;
import static ru.spbau.shavkunov.primitives.PostQuantity.REPOSTS;
import static ru.spbau.shavkunov.primitives.PostQuantity.VIEWS;

/**
 * Class describes information of vk post. (see https://vk.com/dev/post).
 * These properties will be saved in database.
 */
@Entity
public class Post {
    @Id
    private @Nullable String id;

    // save some other statistics to show user
    private @NotNull String text;
    private @Nullable String defaultImage;
    private @NotNull String description;
    private @NotNull String postLink;
    private int likes;
    private int reposts;
    private int views;

    @ElementCollection(targetClass=String.class)
    private @Nullable List<String> images;

    public Post(@NotNull User owner, @NotNull Map json, @NotNull PostCategory category) {
        text = (String) json.get("text");
        likes = (int) ((Map) json.get(LIKES.toString())).get("count");
        reposts = (int) ((Map) json.get(REPOSTS.toString())).get("count");
        views = (int) ((Map) json.get(VIEWS.toString())).get("count");
        description = category.toString();
        postLink = getPostLink(owner, json);

        if (countPhotoAttachments(json) == 0) {
            defaultImage = owner.getPhoto().toString();
            return;
        }

        images = getAttachedImageURLs(json);
    }

    private @NotNull String getPostLink(@NotNull User owner, @NotNull Map json) {
        // TODO : replace with string builder
        int postID = (int) json.get("id");
        String url = owner.getLink() + "?w=wall" + owner.getID() + "_" + postID;

        return url;
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

    public @NotNull String getPostLink() {
        return postLink;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes;
    }

    public int getReposts() {
        return reposts;
    }

    public int getViews() {
        return views;
    }

    public @NotNull String getText() {
        return text;
    }

    public @NotNull String getDefaultImage() {
        return defaultImage;
    }

    public @NotNull List<String> getImages() {
        return images;
    }
}
