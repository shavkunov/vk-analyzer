package ru.spbau.shavkunov;

import org.junit.Test;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;

import java.io.IOException;
import java.net.URISyntaxException;

public class ManagerVKTest {
    @Test
    @SuppressWarnings("unchecked")
    public void rightResponseTest() throws InvalidAmountException, IOException, URISyntaxException {
        ManagerVK manager = new ManagerVK("alfabang", 10);
        //System.out.println(new MapPrinter<>(manager.identify()));
    }
}