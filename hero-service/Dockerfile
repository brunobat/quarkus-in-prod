FROM openjdk:11.0.4-slim-buster
WORKDIR /work
COPY target/lib/* /work/lib/
COPY target/*-runner.jar /work/app.jar
RUN chmod 775 /work
EXPOSE 8080
CMD ["java", "-jar", "/work/app.jar"]
