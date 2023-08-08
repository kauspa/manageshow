package com.demo.manageshow.controller;

import com.demo.manageshow.data.*;
import com.demo.manageshow.service.BookingService;
import com.demo.manageshow.service.BuyerService;
import com.demo.manageshow.service.InvalidException;
import com.demo.manageshow.service.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class BookingController {
    private static final Logger L = LoggerFactory.getLogger(BookingController.class);
    private final BuyerService buyerService;
    private final ShowService showService;
    private final BookingService bookingService;

    public BookingController(BuyerService buyerService, ShowService showService, BookingService bookingService) {
        this.buyerService = buyerService;
        this.showService = showService;
        this.bookingService = bookingService;
    }

    @GetMapping("/availability/{showId}")
    public Map<String, List<Seat>> availableSeats(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() -> new InvalidException(String.format("Show Id %s NOT found", showId)));

        Set<Booking> bookings = bookingService.getBookingByShow(show);
        return null;
    }

    @GetMapping("/bookings/{showId}")
    public Set<Booking> booking(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() ->
                new InvalidException(String.format("Show Id %s NOT found",showId)));
        return bookingService.getBookingByShow(show);
    }

    @PostMapping("/book")
    public Booking bookTicket(@RequestBody BookingRequest bookingRequest) {
        L.debug("bookTicket for {}", bookingRequest);
        Buyer buyer = buyerService.getBuyerByMobile(bookingRequest.getMobile()).orElseThrow(() -> new InvalidException(String.format("Mobile no. %s NOT found",bookingRequest.getMobile())));
        Show show = showService.getShowById(bookingRequest.getShowId()).orElseThrow(() -> new InvalidException(String.format("Show Id %s NOT found",bookingRequest.getShowId())));
        Booking booking = bookingService.bookTicket(buyer, show, bookingRequest.getSeats());
        L.debug("bookTicket end {}", booking);
        return booking;
    }

    @PostMapping("/cancel")
    public Booking cancelTicket(@RequestBody BookingRequest cancelRequest) {
        Buyer buyer = buyerService.getBuyerByMobile(cancelRequest.getMobile()).orElseThrow(() ->
                new InvalidException(String.format("Mobile no. %s NOT found",cancelRequest.getMobile())));
        Show show = showService.getShowById(cancelRequest.getShowId()).orElseThrow(() ->
                new InvalidException(String.format("Show Id %s NOT found",cancelRequest.getShowId())));
        return bookingService.cancelTicket(buyer, show);
    }

    @PostMapping("/login")
    public Buyer login(@RequestBody Buyer buyer) {
        return buyerService.addBuyer(buyer);
    }
}
