package com.demo.manageshow.controller;

import com.demo.manageshow.data.Booking;
import com.demo.manageshow.data.BookingRequest;
import com.demo.manageshow.data.Buyer;
import com.demo.manageshow.data.Show;
import com.demo.manageshow.service.BookingService;
import com.demo.manageshow.service.BuyerService;
import com.demo.manageshow.service.InvalidException;
import com.demo.manageshow.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
public class BookingController {
    private BuyerService buyerService;
    private ShowService showService;
    private BookingService bookingService;

    public BookingController(BuyerService buyerService, ShowService showService, BookingService bookingService) {
        this.buyerService = buyerService;
        this.showService = showService;
        this.bookingService = bookingService;
    }

    @GetMapping("/availability/{showId}")
    public Map<String, Set<Integer>> availableSeats(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() -> new InvalidException("Show Id %s NOT found".format(showId)));

        Set<Booking> bookings = bookingService.getBookingByShow(show);
        return null;
    }

    @GetMapping("/bookings/{showId}")
    public Set<Booking> booking(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() ->
                new InvalidException("Show Id %s NOT found".format(showId)));
        return bookingService.getBookingByShow(show);
    }

    @PostMapping("/book")
    public Booking bookTicket(@RequestBody BookingRequest bookingRequest) {
        Buyer buyer = buyerService.getBuyerByMobile(bookingRequest.getMobile()).orElseThrow(() -> new InvalidException("Mobile no. %s NOT found".format(bookingRequest.getMobile())));
        Show show = showService.getShowById(bookingRequest.getShowId()).orElseThrow(() -> new InvalidException("Show Id %s NOT found".format(bookingRequest.getShowId())));
        return bookingService.bookTicket(buyer, show, bookingRequest.getSeats());
    }

    @PostMapping("/cancel")
    public Booking cancelTicket(@RequestBody BookingRequest cancelRequest) {
        Buyer buyer = buyerService.getBuyerByMobile(cancelRequest.getMobile()).orElseThrow(() ->
                new InvalidException("Mobile no. %s NOT found".format(cancelRequest.getMobile())));
        Show show = showService.getShowById(cancelRequest.getShowId()).orElseThrow(() ->
                new InvalidException("Show Id %s NOT found".format(cancelRequest.getShowId())));
        return bookingService.cancelTicket(buyer, show);
    }

    @PostMapping("/login")
    public Buyer login(@RequestBody Buyer buyer) {
        return buyerService.addBuyer(buyer);
    }
}
