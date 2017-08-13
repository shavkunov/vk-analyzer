package ru.spbau.shavkunov.users;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import java.net.URL;

/**
 * A person wrap.
 * See https://vk.com/dev/fields.
 */
@Entity
public class Person extends User {
    private @NotNull String surname;

    public Person(@NotNull String name, @NotNull String surname, @NotNull String ID, @NotNull URL url) {
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.photo = url;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public int getID() {
        return Integer.parseInt(ID);
    }
}
