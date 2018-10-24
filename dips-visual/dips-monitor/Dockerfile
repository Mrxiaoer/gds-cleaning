FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-monitor

WORKDIR /dips-cloud/bin/com.cloud/dips-monitor

EXPOSE 5001

ADD ./dips-visual/dips-monitor/target/dips-monitor.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-monitor.jar
