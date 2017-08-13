package ru.spbau.shavkunov.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller used to map address of website to index.html.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public @NotNull String index() {
        return "index.html";
    }
}
