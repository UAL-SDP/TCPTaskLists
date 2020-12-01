FROM openjdk:15-slim-buster
RUN apt-get update ; apt-get install -y iproute2 iputils-ping
WORKDIR /app