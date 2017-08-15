package ru.spbau.shavkunov.controllers;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spbau.shavkunov.ManagerVK;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.exceptions.InvalidPageLinkException;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.services.DataRepository;

import java.util.Map;

import static ru.spbau.shavkunov.controllers.ResponseCode.*;

/**
 * Class which handles client requests.
 */
@RestController
public class RequestController {

    @Autowired
    private @NotNull DataRepository repository;

    @RequestMapping(value = "/getStats", method = RequestMethod.POST)
    public @NotNull Response getStatistics(@RequestBody String json)  {
        ObjectMapper mapper = JsonFactory.create();
        Map request = mapper.fromJson(json, Map.class);
        String pageLink = (String) request.get("link");
        int postsAmount = (int) request.get("posts");

        // TODO : replace with static call
        try {
            ManagerVK vk = new ManagerVK(pageLink, postsAmount);
            Response response = new Response(OK, vk.getStatistics());
            return response;
        } catch (InvalidAmountException exception) {
            return new Response(INVALID_AMOUNT, null);
        } catch (InvalidPageLinkException exception) {
            return new Response(INVALID_LINK, null);
        } catch (Exception e) {
            return new Response(INTERNAL_ERROR, null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    public void saveStats(@RequestBody @NotNull Statistics stats) {
        repository.save(stats);
    }
}