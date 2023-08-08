package com.demo.manageshow.service;

import com.demo.manageshow.service.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ManageShowService {
    private static final Logger L = LoggerFactory.getLogger(ManageShowService.class);

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public ManageShowService(@Value("${server.url}") String baseUrl, RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void viewShow(String showId) {
        ResponseEntity<List<Show>> response = restTemplate.exchange(baseUrl + "/shows", HttpMethod.GET, null, new ParameterizedTypeReference<List<Show>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            for (Show s : response.getBody()) {
                print(s);
            }
            ResponseEntity<Collection<Booking>> showBookingResponse = restTemplate.exchange(baseUrl + "/bookings/" + showId, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Booking>>() {
            });
            if (showBookingResponse.getStatusCode() == HttpStatus.OK) {
                Collection<Booking> bookings = showBookingResponse.getBody();
                L.info("ShowId,Ticket#,Buyer Phone#,Seat numbers allocated to the buyer");
                L.info("-------------------------------------------------------------------");
                bookings.forEach(b -> L.info("{},{},{},{}", b.getShow().getShowId(), b.getTicketNo(),
                        b.getBuyer().getMobile(),
                        b.getReservedSeats().stream().map(Seat::getSeatName).collect(Collectors.toList())));
            } else {
                L.error("Show booking failed {}", showBookingResponse);
            }
        } else {
            L.error("viewShow failed. Response={}", response);
        }
    }

    public void setUpShow(Show show) {
        try {
            Show addedShow = restTemplate.postForObject(baseUrl + "/shows", show, Show.class);
            L.info("Show added successfully");
            print(addedShow);
        } catch (RestClientException ex) {
            L.error("setUpShow failed.", ex);
        }
    }

    private static void print(Show addedShow) {
        L.info("ShowId,Show name,Rows,Seats per row,Cancellation duration in minutes");
        L.info("-------------------------------------------------------------------");
        L.info("{},{},{},{},{}", addedShow.getShowId(), addedShow.getShowName(), addedShow.getRows(), addedShow.getSeats(), addedShow.getCancellationMinutesAfterBooking());
    }

    public void viewAvailability(String showId) {
        ResponseEntity<Collection<Seat>> response = restTemplate.exchange(baseUrl + "/availability/" + showId, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Seat>>() {
        });
        if (response.getStatusCode() == HttpStatus.OK) {
            Collection<Seat> availableSeat = response.getBody();
            Map<Integer, List<Seat>> rowWiseMap = availableSeat.stream().collect(Collectors.groupingBy(Seat::getRow));
            L.info("Show={} Availability", showId);
            L.info("-------------------------------------------------------------------");
            rowWiseMap.forEach((r, seats) -> L.info("Row={}| {}", r, seats));

        } else {
            L.error("viewAvailability failed. Response={}", response);
        }
    }

    public void bookTicket(BookingRequest bookingRequest) {
        ResponseEntity<Buyer> buyerResponse = null;
        try {
            buyerResponse = restTemplate.getForEntity(baseUrl + "/buyer/" + bookingRequest.getMobile(), Buyer.class);
        } catch (RestClientException ex) {
            L.error("Buyer not found {}", buyerResponse);
            L.info("Adding Phone {}", bookingRequest.getMobile());
            Buyer buyer = new Buyer();
            buyer.setMobile(bookingRequest.getMobile());
            ResponseEntity<Buyer> addedBuyerResponse = restTemplate.postForEntity(baseUrl + "/buyer", buyer, Buyer.class);
            if (addedBuyerResponse.getStatusCode() == HttpStatus.OK) {
                L.info("Phone {} added successfully", addedBuyerResponse.getBody().getMobile());
            } else {
                L.error("Adding Phone {} failed. {}", bookingRequest.getMobile(), addedBuyerResponse);
            }
        }
        L.info("Booking ticket {}...", bookingRequest);
        ResponseEntity<Booking> bookingResponse = restTemplate.postForEntity(baseUrl + "/book", bookingRequest, Booking.class);
        if (bookingResponse.getStatusCode() == HttpStatus.OK) {
            L.info("Booking {} added successfully", bookingResponse.getBody());
        } else {
            L.error("Booking {} ticket failed. {}", bookingRequest, bookingResponse);
        }


    }

    public void cancelTicket(CancelRequest cancelRequest) {
        ResponseEntity<Booking> cancelBookingResponse = restTemplate.postForEntity(baseUrl + "/cancel", cancelRequest, Booking.class);
        if (cancelBookingResponse.getStatusCode() == HttpStatus.OK) {
            L.info("Booking {} cancelled successfully", cancelBookingResponse.getBody());
        } else {
            L.error("Cancel {} failed. {}", cancelRequest, cancelBookingResponse);
        }
    }
}
