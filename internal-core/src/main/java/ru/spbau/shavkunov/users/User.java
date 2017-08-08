package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public abstract class User {
    protected @NotNull String name;
    protected @NotNull String ID;
    protected @NotNull URL photo;

    public abstract int getID();

    public URL getPhoto() {
        return photo;
    }
}
