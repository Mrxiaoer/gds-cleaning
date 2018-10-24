FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-upms

WORKDIR /dips-cloud/bin/com.cloud/dips-upms

EXPOSE 4000

ADD ./dips-modules/dips-upms-biz/target/dips-upms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-upms-biz.jar
