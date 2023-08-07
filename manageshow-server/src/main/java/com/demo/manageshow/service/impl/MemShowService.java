package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.InvalidException;
import com.demo.manageshow.service.ShowService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemShowService implements ShowService {
    private static final String PREFIX = "Show-";
    AtomicInteger showCounter = new AtomicInteger();
    ConcurrentHashMap<String, Show> showMap = new ConcurrentHashMap<>();

    @Override
    public Show addShow(Show show) {
        if (ShowService.isValid(show)) {
            Show newShow = new Show(PREFIX + showCounter.incrementAndGet(), show.getRows(), show.getSeats());
            newShow.setShowName(show.getShowName());
            return showMap.put(newShow.getShowId(), newShow);
        } else {
            throw new InvalidException("Invalid rows or seats in %s".format(show.toString()));
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
