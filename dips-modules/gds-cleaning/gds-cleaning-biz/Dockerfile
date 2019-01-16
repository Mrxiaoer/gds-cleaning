FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 331228638@qq.com

RUN mkdir -p /dips-cloud/bin/com.cloud/gds-cleaning

WORKDIR /dips-cloud/bin/com.cloud/gds-cleaning

EXPOSE 4335

ADD ./dips-modules/gds-cleaning/gds-cleaning-biz/target/gds-cleaning-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar gds-cleaning-biz.jar
