# manageshow application
## running manageshow-server
```bash
gradlew.bat manageshow-server:bootRun


curl -i -X POST -d {\"firstName\":\"Smith\",\"mobile\":9796185552,\"lastName\":\"dan\"} --header "Content-Type:application/json" http://localhost:8181/login
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
