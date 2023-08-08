# Getting Started
manageshow-client is Spring framework based console application interface. I take command line arguments to perform various application operations

**NOTE: manageshow-server must be running on same host on 8181 port**
```bash
Sample commands:

gradlew.bat manageshow-client:run
gradlew.bat manageshow-client:run --args="setup Drama-1 26 10 10"
gradlew.bat manageshow-client:run --args="view Drama-1"
gradlew.bat manageshow-client:run --args="availablity Drama-1"
gradlew.bat manageshow-client:run --args="availability Drama-1"
gradlew.bat manageshow-client:run --args="book Drama-1 999999999 A1,A2,A3"
gradlew.bat manageshow-client:run --args="availability Drama-1"
gradlew.bat manageshow-client:run --args="view Drama-1"
gradlew.bat manageshow-client:run --args="cancel 1 Drama-1"
gradlew.bat manageshow-client:run --args="cancel 1 999999999"


```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.2/gradle-plugin/reference/html/)
