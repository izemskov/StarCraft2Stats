package ru.develgame.sc2stats.battlenet.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ZulController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("battlenet")
    public String battleNet() {
        return "battlenet";
    }
}
