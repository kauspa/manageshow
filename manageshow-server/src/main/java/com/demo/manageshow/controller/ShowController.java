package com.demo.manageshow.controller;

import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.NotFoundException;
import com.demo.manageshow.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/shows")
    Show newShow(@RequestBody Show newShow) {
        return showService.addShow(newShow);
    }

    @GetMapping("/shows")
    Set<Show> getAll() {
        return showService.getShows();
    }

    @GetMapping("/shows/{showId}")
    Show getById(@PathVariable String showId) {
        Optional<Show> show = showService.getShowById(showId);
        return show.orElseThrow(() -> new NotFoundException(String.format("Show %s NOT found",showId)));
    }

}
