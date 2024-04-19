FROM gradle:8.7-jdk21

RUN mkdir -p /project/web-api
WORKDIR /project/web-api

EXPOSE 8080

CMD ["gradle", "bootRun", "-x", "test"]