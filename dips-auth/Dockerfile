FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER swj956@gmail.com

RUN mkdir -p /dips-cloud/bin/com.cloud/dips-auth

WORKDIR /dips-cloud/bin/com.cloud/dips-auth

EXPOSE 3000

ADD ./dips-auth/target/dips-auth.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar dips-auth.jar
