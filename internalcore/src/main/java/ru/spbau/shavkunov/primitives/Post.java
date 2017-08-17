package ru.spbau.shavkunov.primitives;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.network.Parameter;
import ru.spbau.shavkunov.users.User;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.spbau.shavkunov.ManagerVK.getRequestUrl;
import static ru.spbau.shavkunov.network.Method.PHOTO_GET_BY_ID;
import static ru.spbau.shavkunov.primitives.PostQuantity.*;

/**
 * Class describes information of vk post. (see https://vk.com/dev/post).
 * These properties will be saved in database.
 */
@Entity
public class Post {
    @Id
    private @Nullable String id;

    // TODO : save other post's information

    /**
     * Posts text.
     */
    private @NotNull String text;

    /**
     * Posts description. It's mean that post has best/worst parameter.
     * Parameters are likes, reposts and views.
     */
    private @NotNull String description;

    /**
     * Url to post.
     */
    private @NotNull String postLink;

    // amount of each parameters.
    private int likes;
    private int reposts;
    private int views;

    /**
     * Images attached to post
     */
    @ElementCollection(targetClass=String.class)
    private @Nullable List<String> images;

    public Post(@NotNull User owner, @NotNull Map json, @NotNull PostCategory category) throws IOException {
        text = (String) json.get("text");
        likes = (int) ((Map) json.get(LIKES.toString())).get("count");
        reposts = (int) ((Map) json.get(REPOSTS.toString())).get("count");
        views = (int) ((Map) json.get(VIEWS.toString())).get("count");
        description = category.toString();
        postLink = getPostLink(owner, json);

        if (countPhotoAttachments(json) == 0) {
            images = new ArrayList<>();
            images.add(owner.getPhoto().toString());
            return;
        }

        images = getAttachedImageURLs(owner, json);
    }

    private @NotNull String getPostLink(@NotNull User owner, @NotNull Map json) {
        // TODO : replace with string builder
        int postID = (int) json.get("id");
        String url = owner.getLink() + "?w=wall" + owner.getID() + "_" + postID;

        return url;
    }

    private @NotNull List<String> getAttachedImageURLs(@NotNull User owner, @NotNull Map json) throws IOException {
        List<Map> attach = (List) json.get("attachments");
        List<String> photoURLs = attach.stream()
                .filter(map -> map.get("type").equals("photo"))
                .map(map -> (Map) map.get("photo"))
                .map(map -> (Integer) map.get("id"))
                .map(originalID -> getImageURL(owner, originalID))
                .collect(Collectors.toList());

        // TODO: make other class to manage with photos.
        return photoURLs;
    }

    private @Nullable String getImageURL(@NotNull User owner, @NotNull Integer originalID) {
        try {
            String photoID = owner.getID() + "_" + originalID;
            URL isUserRequest = getRequestUrl(PHOTO_GET_BY_ID,
                    new Parameter("photos", photoID));
            HttpURLConnection connection = (HttpURLConnection) isUserRequest.openConnection();
            ObjectMapper mapper = JsonFactory.create();
            Map photoResponse = mapper.fromJson(connection.getInputStream(), Map.class);
            Map response = (Map) ((List) photoResponse.get("response")).get(0);
            return (String) response.get("src_big");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private long countPhotoAttachments(@NotNull Map json) {
        List<Map> attach = (List) json.get("attachments");

        if (attach == null) {
            return 0;
        }
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

    public @NotNull List<String> getImages() {
        return images;
    }
}
