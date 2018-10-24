FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-codegen

WORKDIR /dips-cloud/bin/com.cloud/dips-codegen

EXPOSE 5003

ADD ./dips-visual/dips-codegen/target/dips-codegen.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-codegen.jar
