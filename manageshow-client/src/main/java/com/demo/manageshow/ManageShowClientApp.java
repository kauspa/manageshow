package com.demo.manageshow;


import com.demo.manageshow.service.ManageShowService;
import com.demo.manageshow.service.data.BookingRequest;
import com.demo.manageshow.service.data.CancelRequest;
import com.demo.manageshow.service.data.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageShowClientApp {
    private static final Logger L = LoggerFactory.getLogger(ManageShowClientApp.class);

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ManageShowClientConfig.class);
        ManageShowService manageShowService = ctx.getBean(ManageShowService.class);
        if (args.length == 0) {
            L.info("Missing arguments. Following are available commands");
            L.info("setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>");
            L.info("view <Show Number>");
            L.info("availability  <Show Number>");
            L.info("book  <Show Number> <Phone#> <Comma separated list of seats> ");
            L.info("cancel  <Ticket#>  <Phone#>");

        } else {
            String verb = args[0];
            switch (CommandEnum.valueOf(verb)) {
                case setup:
                    L.info("setup show");
                    Show show = new Show();
                    show.setShowId(args[1]);
                    show.setShowName(args[1]);
                    show.setRows(Integer.parseInt(args[2]));
                    show.setSeats(Integer.parseInt(args[3]));
                    show.setCancellationMinutesAfterBooking(Integer.parseInt(args[4]));
                    manageShowService.setUpShow(show);
                    break;
                case view:
                    L.info("view show");
                    manageShowService.viewShow(args[1]);
                    break;
                case availability:
                    L.info("show availability");
                    manageShowService.viewAvailability(args[1]);
                    break;
                case book:
                    String[] seatNames = args[3].split(",");
                    Set<String> reserveSeat = Stream.of(seatNames).collect(Collectors.toSet());
                    BookingRequest bookingRequest = new BookingRequest(args[1], args[2], reserveSeat);
                    manageShowService.bookTicket(bookingRequest);
                    break;
                case cancel:
                    CancelRequest cancelRequest = new CancelRequest(args[1], args[2]);
                    manageShowService.cancelTicket(cancelRequest);
                    break;
            }
        }

    }

}
