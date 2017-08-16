package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mikhail Shavkunov
 */
public class Request {
    private @NotNull String link;
    private @NotNull String posts;

    public Request(@NotNull String link, String posts) {
        this.link = link;
        this.posts = posts;
    }

    public String getLink() {
        return link;
    }

    public String getPosts() {
        return posts;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public Request() {
    }
}
