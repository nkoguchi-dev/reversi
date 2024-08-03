FROM --platform=linux/amd64 selenium/standalone-chrome

RUN sudo mkdir -p /opt/e2e-test
WORKDIR /opt/e2e-test

COPY . .
RUN sudo chown -R seluser:seluser /opt/e2e-test
RUN ls -la /opt/e2e-test

COPY entrypoint.sh /entrypoint.sh
RUN sudo chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
