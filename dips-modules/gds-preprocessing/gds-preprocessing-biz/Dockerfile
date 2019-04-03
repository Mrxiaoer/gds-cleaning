FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 806039077@qq.com

RUN mkdir -p /dips-cloud/bin/com.cloud/gds-preprocessing

WORKDIR /dips-cloud/bin/com.cloud/gds-preprocessing

EXPOSE 3020

ADD ./dips-modules/gds-preprocessing/gds-preprocessing-biz/target/gds-preprocessing-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar gds-preprocessing-biz.jar
