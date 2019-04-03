FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 806039077@qq.com

RUN mkdir -p /dips-cloud/bin/com.cloud/gds-gmsanalyse

WORKDIR /dips-cloud/bin/com.cloud/gds-gmsanalyse

EXPOSE 3022

ADD ./dips-modules/gds-gmsanalyse/gds-gmsanalyse-biz/target/gds-gmsanalyse-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar gds-gmsanalyse-biz.jar
