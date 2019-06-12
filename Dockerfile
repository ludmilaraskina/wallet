FROM gradle:5.4.0-jdk11 as builder

COPY --chown=gradle:gradle . .

RUN gradle --no-daemon clean build


FROM openjdk:11-oracle

RUN useradd --no-log-init -r walletuser

WORKDIR /app

USER walletuser
COPY --from=builder --chown=walletuser:walletuser /home/gradle/build/libs/server*.jar /app/server.jar

ENV ACTIVE_PROFILE="default"
ENV JVM_OPTS=""

EXPOSE 8080

ENTRYPOINT exec java $JVM_OPTS -jar /app/server.jar --spring.profiles.active=$ACTIVE_PROFILE