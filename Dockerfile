# Runtime
FROM openjdk:11-jre-slim

WORKDIR /app

COPY entrypoint.sh ./

COPY target/lib/* /app/lib/
COPY target/*-runner.jar /app/app.jar

EXPOSE 8080

CMD ["/app/entrypoint.sh"]
