package com.demo.manageshow.service.impl;

import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.DuplicateException;
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
            String showId = show.getShowId() != null && !show.getShowId().isBlank() ?
                    show.getShowId() : PREFIX + showCounter.incrementAndGet();
            Show newShow = new Show(showId, show.getRows(), show.getSeats());
            newShow.setShowName(show.getShowName());
            Show existing = showMap.putIfAbsent(newShow.getShowId(), newShow);
            if (existing != null) {
                throw new DuplicateException(String.format("Show %s already exists!", newShow));
            }
            return newShow;
        } else {
            throw new InvalidException(String.format("Invalid rows or seats in %s. Max rows=%d, Max seats=%d",
                    show.toString(), ShowService.ROWS_MAX, ShowService.SEATS_MAX));
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
