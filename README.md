mvp version of TAF for testing REST services 
========================

This is a pilot project demonstrating how to use TestNG with RestAssured and Maven.


### How to run

To run All Rest API tests, just type from command line:

```
mvn verify
```

In order to run them in parallel, just type from command line:

```
mvn verify -Dparallel=methods -DthreadCount=2
```

### How to generate Allure report

1. [Install Allure server](https://docs.qameta.io/allure/latest/#_get_started)
2. Perform in command line:
```
allure serve **/target/allure-results
```
