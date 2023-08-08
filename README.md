# ManageShow Application
A simple Java application for the use case of booking a Show. The program would setup available seats per show, allow buyers to select 1 or more available seats and buy/cancel tickets.

Application comprises of manageshow-server, and manageshow-client.
* manageshow-server is Spring Boot based, RESTful service application. It uses java.util.concurrent.ConcurrentHashMap as datastore.
* manageshow-client is Spring framework based console application interface. I take command line arguments to perform various application operations.  

## Running manageshow-server
```bash
gradlew.bat manageshow-server:bootRun

curl -i -X POST -d {\"firstName\":\"Smith\",\"mobile\":9796185552,\"lastName\":\"dan\"} --header "Content-Type:application/json" http://localhost:8181/buyer
curl -i -X POST -d {\"showName\":\"MyShow2\",\"rows\":21,\"seats\":5} --header "Content-Type:application/json" http://localhost:8181/shows
curl http://localhost:8181/shows
curl http://localhost:8181/bookings/Show-1
curl http://localhost:8181/availability/Show-1
curl -i -X POST -d {\"showId\":\"Show-1\",\"mobile\":9796185552,\"seats\":[\"A1\",\"A2\"]} --header "Content-Type:application/json" http://localhost:8181/book
curl -i -X POST -d {\"showId\":\"Show-1\",\"mobile\":9796185552} --header "Content-Type:application/json" http://localhost:8181/cancel
curl http://localhost:8181/bookings/Show-1
curl -i -X POST -d {\"showId\":\"Show-1\",\"mobile\":9796185552,\"seats\":[\"A1\",\"A2\"]} --header "Content-Type:application/json" http://localhost:8181/book
curl http://localhost:8181/bookings/Show-1
curl -i -X POST -d {\"ticketNo\":\"2\",\"mobile\":9796185552} --header "Content-Type:application/json" http://localhost:8181/cancel
curl http://localhost:8181/bookings/Show-1
```
## Running manageshow-client
```bash
1. gradlew.bat manageshow-client:run

> Task :manageshow-client:run
22:58:05.285 [main] INFO  c.d.manageshow.ManageShowClientApp - Missing arguments. Following are available commands
22:58:05.287 [main] INFO  c.d.manageshow.ManageShowClientApp - setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>
22:58:05.287 [main] INFO  c.d.manageshow.ManageShowClientApp - view <Show Number>
22:58:05.287 [main] INFO  c.d.manageshow.ManageShowClientApp - availability  <Show Number>
22:58:05.287 [main] INFO  c.d.manageshow.ManageShowClientApp - book  <Show Number> <Phone#> <Comma separated list of seats>
22:58:05.287 [main] INFO  c.d.manageshow.ManageShowClientApp - cancel  <Ticket#>  <Phone#>

2. gradlew.bat manageshow-client:run --args="setup Drama-1 26 10 10"

> Task :manageshow-client:run
23:01:26.052 [main] INFO  c.d.manageshow.ManageShowClientApp - setup show
23:01:26.300 [main] INFO  c.d.m.service.ManageShowService - Show added successfully
23:01:26.300 [main] INFO  c.d.m.service.ManageShowService - ShowId,Show name,Rows,Seats per row,Cancellation duration in minutes
23:01:26.300 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:01:26.300 [main] INFO  c.d.m.service.ManageShowService - Drama-1,Drama-1,26,10,2

3. gradlew.bat manageshow-client:run --args="view Drama-1"

> Task :manageshow-client:run
23:02:44.562 [main] INFO  c.d.manageshow.ManageShowClientApp - view show
23:02:44.656 [main] INFO  c.d.m.service.ManageShowService - ShowId,Show name,Rows,Seats per row,Cancellation duration in minutes
23:02:44.656 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:02:44.656 [main] INFO  c.d.m.service.ManageShowService - Drama-1,Drama-1,26,10,2
23:02:44.672 [main] INFO  c.d.m.service.ManageShowService - ShowId,Ticket#,Buyer Phone#,Seat numbers allocated to the buyer
23:02:44.672 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------

4. gradlew.bat manageshow-client:run --args="availability Drama-1"

> Task :manageshow-client:run
23:03:36.267 [main] INFO  c.d.manageshow.ManageShowClientApp - show availability
23:03:36.363 [main] INFO  c.d.m.service.ManageShowService - Show=Drama-1 Availability
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=1| [A1, A2, A3, A4, A5, A6, A7, A8, A9, A10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=2| [B1, B2, B3, B4, B5, B6, B7, B8, B9, B10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=3| [C1, C2, C3, C4, C5, C6, C7, C8, C9, C10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=4| [D1, D2, D3, D4, D5, D6, D7, D8, D9, D10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=5| [E1, E2, E3, E4, E5, E6, E7, E8, E9, E10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=6| [F1, F2, F3, F4, F5, F6, F7, F8, F9, F10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=7| [G1, G2, G3, G4, G5, G6, G7, G8, G9, G10]
23:03:36.364 [main] INFO  c.d.m.service.ManageShowService - Row=8| [H1, H2, H3, H4, H5, H6, H7, H8, H9, H10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=9| [I1, I2, I3, I4, I5, I6, I7, I8, I9, I10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=10| [J1, J2, J3, J4, J5, J6, J7, J8, J9, J10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=11| [K1, K2, K3, K4, K5, K6, K7, K8, K9, K10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=12| [L1, L2, L3, L4, L5, L6, L7, L8, L9, L10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=13| [M1, M2, M3, M4, M5, M6, M7, M8, M9, M10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=14| [N1, N2, N3, N4, N5, N6, N7, N8, N9, N10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=15| [O1, O2, O3, O4, O5, O6, O7, O8, O9, O10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=16| [P1, P2, P3, P4, P5, P6, P7, P8, P9, P10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=17| [Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=18| [R1, R2, R3, R4, R5, R6, R7, R8, R9, R10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=19| [S1, S2, S3, S4, S5, S6, S7, S8, S9, S10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=20| [T1, T2, T3, T4, T5, T6, T7, T8, T9, T10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=21| [U1, U2, U3, U4, U5, U6, U7, U8, U9, U10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=22| [V1, V2, V3, V4, V5, V6, V7, V8, V9, V10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=23| [W1, W2, W3, W4, W5, W6, W7, W8, W9, W10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=24| [X1, X2, X3, X4, X5, X6, X7, X8, X9, X10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=25| [Y1, Y2, Y3, Y4, Y5, Y6, Y7, Y8, Y9, Y10]
23:03:36.365 [main] INFO  c.d.m.service.ManageShowService - Row=26| [Z1, Z2, Z3, Z4, Z5, Z6, Z7, Z8, Z9, Z10]

5. gradlew.bat manageshow-client:run --args="book Drama-1 999999999 A1,A2,A3"

> Task :manageshow-client:run
23:04:48.073 [main] ERROR c.d.m.service.ManageShowService - Buyer not found null
23:04:48.075 [main] INFO  c.d.m.service.ManageShowService - Adding Phone 999999999
23:04:48.102 [main] INFO  c.d.m.service.ManageShowService - Phone 999999999 added successfully
23:04:48.102 [main] INFO  c.d.m.service.ManageShowService - Booking ticket BookingRequest{showId='Drama-1', mobile='999999999', seats=[A1, A2, A3]}...
23:04:48.133 [main] INFO  c.d.m.service.ManageShowService - Booking Booking{ticketNo='1', show=Show{showId='Drama-1', showName='Drama-1', rows=26, seats=10}, buyer=Buyer{firstName='null', lastName='null', mobile='999999999'}, reservedSeats=[A1, A3, A2], bookingTime=2023-08-08T23:04:48.1238557} added successfully

6. gradlew.bat manageshow-client:run --args="availability Drama-1"

> Task :manageshow-client:run
23:05:23.730 [main] INFO  c.d.manageshow.ManageShowClientApp - show availability
23:05:23.826 [main] INFO  c.d.m.service.ManageShowService - Show=Drama-1 Availability
23:05:23.829 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:05:23.830 [main] INFO  c.d.m.service.ManageShowService - Row=1| [A4, A5, A6, A7, A8, A9, A10]
23:05:23.830 [main] INFO  c.d.m.service.ManageShowService - Row=2| [B1, B2, B3, B4, B5, B6, B7, B8, B9, B10]
23:05:23.830 [main] INFO  c.d.m.service.ManageShowService - Row=3| [C1, C2, C3, C4, C5, C6, C7, C8, C9, C10]
23:05:23.830 [main] INFO  c.d.m.service.ManageShowService - Row=4| [D1, D2, D3, D4, D5, D6, D7, D8, D9, D10]
23:05:23.830 [main] INFO  c.d.m.service.ManageShowService - Row=5| [E1, E2, E3, E4, E5, E6, E7, E8, E9, E10]
(Output truncated in readme file)

7. gradlew.bat manageshow-client:run --args="view Drama-1"

> Task :manageshow-client:run
23:05:56.595 [main] INFO  c.d.manageshow.ManageShowClientApp - view show
23:05:56.685 [main] INFO  c.d.m.service.ManageShowService - ShowId,Show name,Rows,Seats per row,Cancellation duration in minutes
23:05:56.686 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:05:56.686 [main] INFO  c.d.m.service.ManageShowService - Drama-1,Drama-1,26,10,2
23:05:56.695 [main] INFO  c.d.m.service.ManageShowService - ShowId,Ticket#,Buyer Phone#,Seat numbers allocated to the buyer
23:05:56.695 [main] INFO  c.d.m.service.ManageShowService - -------------------------------------------------------------------
23:05:56.695 [main] INFO  c.d.m.service.ManageShowService - Drama-1,1,999999999,[A1, A3, A2]

8. gradlew.bat manageshow-client:run --args="cancel 1 999999999"

> Task :manageshow-client:run FAILED
Exception in thread "main" org.springframework.web.client.HttpClientErrorException$Conflict: 409 : "Too late. Cancellation time was 2023-08-08T23:06:48.123855700 for Ticket# 1 booking Booking{ticketNo='1', show=Show{showId='Drama-1', showName='Drama-1', rows=26, seats=10}, buyer=Buyer{firstName='null', lastName='null', mobile='999999999'}, reservedSeats=[A1, A2, A3], bookingTime=2023-08-08T23:04:48.123855700}."
        at org.springframework.web.client.HttpClientErrorException.create(HttpClientErrorException.java:121)
        at org.springframework.web.client.DefaultResponseErrorHandler.handleError(DefaultResponseErrorHandler.java:183)
        at org.springframework.web.client.DefaultResponseErrorHandler.handleError(DefaultResponseErrorHandler.java:137)
        at org.springframework.web.client.ResponseErrorHandler.handleError(ResponseErrorHandler.java:63)
        at org.springframework.web.client.RestTemplate.handleResponse(RestTemplate.java:915)
        at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:864)
        at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:764)
        at org.springframework.web.client.RestTemplate.postForEntity(RestTemplate.java:512)
        at com.demo.manageshow.service.ManageShowService.cancelTicket(ManageShowService.java:119)
        at com.demo.manageshow.ManageShowClientApp.main(ManageShowClientApp.java:60)
```
