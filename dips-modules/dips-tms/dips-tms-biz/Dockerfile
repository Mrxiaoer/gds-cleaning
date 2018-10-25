FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-tms

WORKDIR /dips-cloud/bin/com.cloud/dips-tms

EXPOSE 2010

ADD ./dips-tms-biz/target/dips-tms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-tms-biz.jar
