FROM openjdk:11-oracle as builder

COPY . /wallet
WORKDIR /wallet

RUN ./gradlew --no-daemon clean build
RUN ls /wallet



FROM openjdk:11-oracle

RUN useradd --no-log-init -r walletuser

WORKDIR /app

USER walletuser
COPY --from=builder --chown=walletuser:walletuser /wallet/client/build/libs/client.jar /app/client.jar

ENV ACTIVE_PROFILE="default"
ENV JVM_OPTS=""

EXPOSE 8080

ENTRYPOINT exec java $JVM_OPTS -Dspring.profiles.active=$ACTIVE_PROFILE -jar /app/client.jar $USER_AMOUNT $CONCURRENT_THREADS_PER_USER $ROUNDS_PER_THREAD