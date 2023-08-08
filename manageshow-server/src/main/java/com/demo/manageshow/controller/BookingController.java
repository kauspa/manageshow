package com.demo.manageshow.controller;

import com.demo.manageshow.controller.data.BookingRequest;
import com.demo.manageshow.controller.data.CancelRequest;
import com.demo.manageshow.data.*;
import com.demo.manageshow.service.*;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public Collection<Seat> availableSeats(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() ->
                new NotFoundException(String.format("Show Id %s NOT found", showId)));
        return bookingService.getAvailabilityByShow(show);
    }

    @GetMapping("/bookings/{showId}")
    public Collection<Booking> booking(@PathVariable String showId) {
        Show show = showService.getShowById(showId).orElseThrow(() ->
                new InvalidException(String.format("Show Id %s NOT found", showId)));
        return bookingService.getBookingByShow(show);
    }

    @PostMapping("/book")
    public Booking bookTicket(@RequestBody BookingRequest bookingRequest) {
        L.debug("bookTicket for {}", bookingRequest);
        Buyer buyer = buyerService.getBuyerByMobile(bookingRequest.getMobile()).orElseThrow(() ->
                new NotFoundException(String.format("User phone# %s NOT found", bookingRequest.getMobile())));
        Show show = showService.getShowById(bookingRequest.getShowId()).orElseThrow(() ->
                new NotFoundException(String.format("Show Id %s NOT found", bookingRequest.getShowId())));
        Booking booking = bookingService.bookTicket(buyer, show, bookingRequest.getSeats());
        L.debug("bookTicket end {}", booking);
        return booking;
    }

    @PostMapping("/cancel")
    public Booking cancelTicket(@RequestBody CancelRequest cancelRequest) {
        L.debug("cancelTicket for {}", cancelRequest);
        Buyer buyer = buyerService.getBuyerByMobile(cancelRequest.getMobile()).orElseThrow(() ->
                new InvalidException(String.format("User phone# %s NOT found", cancelRequest.getMobile())));
        Booking cancelledBooking = bookingService.cancelTicket(buyer, cancelRequest.getTicketNo());
        L.debug("cancelTicket end {}", cancelledBooking);
        return cancelledBooking;
    }

    @PostMapping("/buyer")
    public Buyer buyer(@RequestBody Buyer buyer) {
        return buyerService.addBuyer(buyer);
    }

    @GetMapping("/buyer/{phone}")
    public Buyer getBuyer(@PathVariable String phone) {
        Buyer existing = buyerService.getBuyerByMobile(phone).orElseThrow(() ->
                new NotFoundException(String.format("User phone# %s NOT found", phone)));
        return existing;
    }

}
