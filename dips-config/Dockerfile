FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-config

WORKDIR /dips-cloud/bin/com.cloud/dips-config

EXPOSE 4001

ADD ./dips-config/target/dips-config.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-config.jar