FROM rabbitmq:3.7.15-management-alpine

LABEL Name="rabbitmq-docker" Version="3.7.15"

ADD rabbitmq.conf /etc/rabbitmq/
ADD definitions.json /etc/rabbitmq/

# Update packages
RUN set -x \
    && apk update \
    && apk upgrade --available \