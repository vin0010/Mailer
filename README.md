[![Build Status](https://travis-ci.org/vin0010/Mailer.svg?branch=master)](https://travis-ci.org/vin0010/Mailer)

# Mailer
Mailer is a Micro Service responsible for sending asynchronous emails with synchronous acknowledgment.

## Description
This code base provides a micro service capable of sending mails asynchronously. It has been developed using spring boot framework and uses Apache Kafka for messaging.

To make sure things are not over engineered, for starters it uses single consumer and single producer to take care of mail requests.

## Requirement
- REST API with synchronous acknowledgment with only one method for sending new Mail
- Mail with attachment should be possible. (Attachment Content will be provided in the request by a URI pointing to the actual document binaries)
- Queuing until successful response from SMTP Server. Max Retry configurable.
- No Authentication required.

## Tools used
- **Spring boot** framework used to write microservice
- **Kafka** for stream processing
- **Spring Retry** to automatically retry a failed operation  
- **Embedded kafka** for testing
- **Junit 4** for unit tests
- **Swagger** for API documentation
- **Maven** for build
- **Google guava** for commons library
- **Spring mail** to send mail
- **Mockito** for unit tests mock
- **HttpURLConnection** used to download attachment binaries
- **HMailServer** used as a local web mail server.

## Architecture
![Architecture](Architecture.jpg)

## Scalability
- Scalability aspect has been covered since this microservice can scale vertically or horizontally by either spin off more instances with load balancer or introduce more consumers which deal with mail sending asynchronously.

## Future Suggestions
- Number of consumers can be increased since email sending can be run in parallel without any fuss
- Better failure notification mechanism to make sure sender aware of all failures
- New API can be introduced to give statistics of sent/failed mails
- New Health API can be introduced to monitor health of Kafka producers, Consumers, Mail service etc.
- Caching can be introduced to avoid multiple downloads of same attachment.
- Code can be modified such that consumers can be increased based on number of requests.
- Freemaker template can be used when sending html content

## Mail Server
- HmailServer used for development in local machine with a configurable domain and user accounts
- Webmail used as web mail client to check mails and attachments

## Swagger
- Use http://localhost:9090/swagger-ui.html to access swagger once deployed application

## Kafka
- Follow https://medium.com/@shaaslam/installing-apache-kafka-on-windows-495f6f2fd3c8 to set up kafka in local
- Follow https://medium.com/@shaaslam/installing-apache-zookeeper-on-windows-45eda303e835#.fgofwm6n6 to set up zookeeper

## Integration Tests
- Integration tests require running kafka and zookeeper in place.

## Assumptions
- If a mail failed after n(configurable in application.properties) retries, its considered a failure.
- Caching is not implemented inside application
- Attachment can either be an direct url(https://google.com/robots.txt) or a generic one(https://drive.google.com/uc?id=1OjVXW2xK9eHcBAjJJygtcyYtiQBwX-tt&export=download)
- If attachment URI is a direct url, file name will be received from url itself
- If attachment URI is a generic one, file name will be received from content-disposition header.
- If attachment fetching failed without any exception, it will throw exception and mail will not be sent
- Failed records/attempts are need not be persisted in any form inside the application since kafka will store them in memory and recovery even after a complete crash is possible
- Simplistic approach followed during development towards delivery
- Retry to download attachment is not implemented
- Its assumed that request contains exact html/text content to be delivered and it doesn't require any processing.
- As requirement suggests Mailer is asynchronous, thus controller send data to producer and send acceptance acknowledgment.
- As requirement suggests mail controller is synchronous API.

## Others
- Number of retries to send mail can be configured via mailer.retry.maxAttempts in application.properties
- Time gap between retries can be configured via mailer.retry.backOffDelay in application.properties
