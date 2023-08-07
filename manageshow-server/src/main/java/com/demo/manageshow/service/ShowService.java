package com.demo.manageshow.service;

import com.demo.manageshow.data.Show;

import java.util.Optional;
import java.util.Set;

public interface ShowService {
    final int SEATS_MAX = 10;
    final int ROWS_MAX = 26;

    public Show addShow(Show show);

    public Set<Show> getShows();

    public Optional<Show> getShowById(String showId);

    public static boolean isValid(Show s) {
        return s != null && (s.getRows() >= 1 && s.getRows() < ROWS_MAX) && (s.getSeats() >= 1 && s.getSeats() <= SEATS_MAX);
    }
}
