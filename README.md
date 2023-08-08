# manageshow application
## running manageshow-server
```bash
gradlew.bat manageshow-server:bootRun

curl -i -X POST -d {\"showName\":\"MyShow2\",\"rows\":21,\"seats\":5} --header "Content-Type:application/json" http://localhost:8181/shows

curl -i -X POST -d {\"firstName\":\"Pawan\",\"mobile\":9796185551,\"lastName\":\"Kaushal\"} --header "Content-Type:application/json" http://localhost:8181/login

curl -i -X POST -d {\"showId\":\"Show-1\",\"mobile\":9796185551,\"seats\":[\"A1\",\"A2\"]} --header "Content-Type:application/json" http://localhost:8181/book

```