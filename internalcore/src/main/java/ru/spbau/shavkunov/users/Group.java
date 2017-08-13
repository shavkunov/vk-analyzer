package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * A community wrap.
 * See https://vk.com/dev/fields_groups.
 */
public class Group extends User {
    public Group(@NotNull String name, @NotNull String ID, @NotNull URL photoURL) {
        this.photo = photoURL;
        this.name = name;
        this.ID = ID;
    }

    @Override
    public int getID() {
        return -Integer.parseInt(ID);
    }
}
