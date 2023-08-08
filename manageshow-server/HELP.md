# Getting Started
manageshow-server is Spring Boot based, RESTful service application. It uses java.util.concurrent.ConcurrentHashMap as datastore.

**NOTE: manageshow-server used 8181 port by default.**

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

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.2/gradle-plugin/reference/html/)