package ru.spbau.shavkunov.Users;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * Created by Mikhail Shavkunov
 */
public class Person extends User {
    private @NotNull String surname;

    public Person(@NotNull String name, @NotNull String surname, @NotNull String ID, @NotNull URL url) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.photo = url;
    }

    @Override
    public int getID() {
        return Integer.parseInt(ID);
    }
}
