# aw0722

## Description

This is a Java Spring Boot App designed for the Programming Demonstration.

You can run the app by pulling the code and running:

```
mvn spring-boot: run
```

### curl commands for provided test cases

test 1

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=JAKR&rentalDays=5&date=9%2F3%2F15&discount=101'
```

test 2

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=LADW&rentalDays=3&date=7%2F2%2F20&discount=10'
```

test 3

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=CHNS&rentalDays=5&date=7%2F2%2F15&discount=25'
```

test 4

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=JAKD&rentalDays=6&date=9%2F3%2F15&discount=0'
```

test 5

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=JAKR&rentalDays=9&date=7%2F2%2F15&discount=0'
```

test 6

```
curl -i -X 'GET' 'http://localhost:8080/rental/checkout?toolCode=JAKR&rentalDays=4&date=7%2F2%2F20&discount=50'
```