FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 806039077@qq.com

RUN mkdir -p /dips-cloud/bin/com.cloud/gds-gms

WORKDIR /dips-cloud/bin/com.cloud/gds-gms

EXPOSE 3021

ADD ./dips-modules/gds-gms/gds-gms-biz/target/gds-gms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar gds-gms-biz.jar
