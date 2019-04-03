FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 806039077@qq.com

RUN mkdir -p /dips-cloud/bin/com.cloud/gds-gmsnlp

WORKDIR /dips-cloud/bin/com.cloud/gds-gmsnlp

EXPOSE 4335

ADD ./dips-modules/gds-gmsnlp/gds-gmsnlp-biz/target/gds-gmsnlp-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar gds-gmsnlp-biz.jar
