FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-eureka

WORKDIR /dips-cloud/bin/com.cloud/dips-eureka

EXPOSE 1025

ADD ./dips-eureka/target/dips-eureka.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-eureka.jar
