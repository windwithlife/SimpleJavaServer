FROM java:8-jdk-alpine

COPY ./target/example-svc-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch example-svc-1.0.0.jar'

ENTRYPOINT ["java", "-jar", "example-svc-1.0.0.jar"]