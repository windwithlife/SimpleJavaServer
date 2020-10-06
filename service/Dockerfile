FROM java:8-jdk-alpine

COPY ./target/exampleModel-svc-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch exampleModel-svc-1.0.0.jar'

ENTRYPOINT ["java", "-jar", "exampleModel-svc-1.0.0.jar"]