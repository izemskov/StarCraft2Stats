package ru.develgame.sc2stats.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class FrontendController {
    @GetMapping
    public String mainPage() {
        return "index";
    }

    @GetMapping("player")
    public String playerInfo() {
        return "player";
    }

    @GetMapping("team")
    public String teamInfo() {
        return "team";
    }

    @GetMapping("maps")
    public String mapsInfo() {
        return "maps";
    }
}
