FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-gateway

WORKDIR /dips-cloud/bin/com.cloud/dips-gateway

EXPOSE 9999

ADD ./dips-gateway/target/dips-gateway.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-gateway.jar
