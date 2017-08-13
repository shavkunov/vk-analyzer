package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbau.shavkunov.ManagerVK;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.services.DataRepository;

import java.io.IOException;

/**
 * Class which handles client requests.
 */
@RestController
public class RequestController {

    @Autowired
    private @NotNull DataRepository repository;

    @RequestMapping("/link={pageLink}&posts={postsAmount}")
    public @NotNull Statistics getStatistics(@PathVariable @NotNull String pageLink, @PathVariable int postsAmount)
            throws InvalidAmountException, BadJsonResponseException, IOException {
        // TODO : replace with static call
        ManagerVK vk = new ManagerVK(pageLink, postsAmount);

        return vk.getStatistics();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    public void saveStats(@RequestBody @NotNull Statistics stats) {
        repository.save(stats);
    }
}