FROM --platform=linux/amd64 selenium/standalone-chrome

RUN sudo mkdir -p /opt/e2e-test
WORKDIR /opt/e2e-test

COPY . .

RUN sudo chown -R seluser:seluser /opt/e2e-test
RUN chmod +x ./gradlew
RUN ./gradlew clean build

CMD ["./gradlew", "test"]
