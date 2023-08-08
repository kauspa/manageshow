package com.demo.manageshow.service;

import com.demo.manageshow.data.Show;

import java.util.Optional;
import java.util.Set;

public interface ShowService {
    int SEATS_MAX = 10;
    int ROWS_MAX = 26;

    Show addShow(Show show);

    Set<Show> getShows();

    Optional<Show> getShowById(String showId);

    static boolean isValid(Show s) {
        return s != null && (s.getRows() >= 1 && s.getRows() <= ROWS_MAX) && (s.getSeats() >= 1 && s.getSeats() <= SEATS_MAX);
    }
}
