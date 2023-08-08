package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.InvalidException;
import com.demo.manageshow.service.ShowService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MemShowService implements ShowService {
    private static final String PREFIX = "Show-";
    private final AtomicInteger showCounter = new AtomicInteger();
    private final ConcurrentHashMap<String, Show> showMap = new ConcurrentHashMap<>();

    @Override
    public Show addShow(Show show) {
        if (ShowService.isValid(show)) {
            Show newShow = new Show(PREFIX + showCounter.incrementAndGet(), show.getRows(), show.getSeats());
            newShow.setShowName(show.getShowName());
            return showMap.put(newShow.getShowId(), newShow);
        } else {
            throw new InvalidException(String.format("Invalid rows or seats in %s",show.toString()));
        }
    }

    @Override
    public Set<Show> getShows() {
        return new HashSet<>(showMap.values());
    }

    @Override
    public Optional<Show> getShowById(String showId) {
        return Optional.ofNullable(showMap.get(showId));
    }
}
