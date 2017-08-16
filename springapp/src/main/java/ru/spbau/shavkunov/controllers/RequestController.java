package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.spbau.shavkunov.ManagerVK;
import ru.spbau.shavkunov.controllers.response.Request;
import ru.spbau.shavkunov.controllers.response.Response;
import ru.spbau.shavkunov.exceptions.EmptyLinkException;
import ru.spbau.shavkunov.exceptions.InvalidAmountException;
import ru.spbau.shavkunov.exceptions.InvalidPageLinkException;
import ru.spbau.shavkunov.primitives.Statistics;
import ru.spbau.shavkunov.services.DataRepository;

import static ru.spbau.shavkunov.controllers.response.ResponseDescription.*;

/**
 * Class which handles client requests.
 */
@RestController
public class RequestController {

    @Autowired
    private @NotNull DataRepository repository;

    @RequestMapping(value = "/getStats", method = RequestMethod.POST,
                    produces = "application/json", consumes = "application/json")
    public @NotNull Response getStatistics(@RequestBody Request request)  {
        String pageLink = request.getLink();
        String postsAmount = request.getPosts();

        // TODO : replace with static call
        try {
            ManagerVK vk = new ManagerVK(pageLink, postsAmount);
            Response response = new Response(OK, vk.getStatistics());
            return response;
            // TODO: cosmetic fix
        } catch (InvalidPageLinkException exception) {
            exception.printStackTrace();
            return new Response(INVALID_LINK, null);
        } catch (InvalidAmountException exception) {
            exception.printStackTrace();
            return new Response(INVALID_AMOUNT, null);
        } catch (EmptyLinkException exception) {
            exception.printStackTrace();
            return new Response(EMPTY_LINK, null);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Response(INTERNAL_ERROR, null);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveStats")
    public void saveStats(@RequestBody @NotNull Statistics stats) {
        repository.save(stats);
    }
}