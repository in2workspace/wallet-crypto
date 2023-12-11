# temp build
FROM docker.io/gradle:8.4.0 AS TEMP_BUILD
ARG SKIP_TESTS=false
COPY src /home/gradle/src/src
COPY config /home/gradle/src/config
COPY docs /home/gradle/src/docs
COPY gradle /home/gradle/src/gradle
COPY monitoring /home/gradle/src/monitoring
COPY waltid/configs /home/gradle/src/waltid/configs
COPY service-matrix.properties /home/gradle/src/
WORKDIR /home/gradle/src
RUN if [ "$SKIP_TESTS" = "true" ]; then \
    gradle build --no-daemon -x test; \
  else \
    gradle build --no-daemon; \
  fi

# build image
FROM openjdk:17-alpine
RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot
USER nonroot
WORKDIR /app
COPY --from=TEMP_BUILD /home/gradle/src/service-matrix.properties /app/
COPY --from=TEMP_BUILD /home/gradle/src/waltid/configs /app/waltid/configs
COPY --from=TEMP_BUILD /home/gradle/src/build/libs/*.jar /app/wallet-crypto.jar
ENTRYPOINT ["java", "-jar", "/app/wallet-crypto.jar"]
