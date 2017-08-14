package ru.spbau.shavkunov.controllers;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbau.shavkunov.ManagerVK;
import ru.spbau.shavkunov.exceptions.BadJsonResponseException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.services.DataRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static ru.spbau.shavkunov.controllers.Response.ERROR;
import static ru.spbau.shavkunov.controllers.Response.OK;

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

    @RequestMapping(method = RequestMethod.POST, value = "/validateVkLink", consumes = "text/plain")
    public @NotNull Response validateVkLink(@RequestBody String json) {
        ObjectMapper mapper = JsonFactory.create();
        Map linkContainer = mapper.fromJson(json, Map.class);

        String link = (String) linkContainer.get("link");
        try {
            URL request = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) request.openConnection();

            if (connection.getResponseCode() == 404) {
                return ERROR;
            }
        } catch (Exception e) {
            return ERROR;
        }

        return OK;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    public void saveStats(@RequestBody @NotNull Statistics stats) {
        repository.save(stats);
    }
}