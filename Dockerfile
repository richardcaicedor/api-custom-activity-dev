ARG IMAGE=public.ecr.aws/docker/library/maven:3.8.5-openjdk-17-slim
ARG IMAGE_BUILDER=public.ecr.aws/docker/library/maven:3.8.5-openjdk-17-slim



FROM ${IMAGE_BUILDER} as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM ${IMAGE}
WORKDIR /app

COPY --from=builder spring-boot-loader ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder dependencies/ ./
COPY --from=builder application/ ./

#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


# Copiar el archivo JAR en la carpeta /opt
EXPOSE 8080 5000

# Definir la variable de entorno JAVA_TOOL_OPTIONS con valor por defecto
ENV JAVA_TOOL_OPTIONS="-Dcom.sun.management.jmxremote.ssl=false \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.port=5000 \
 -Dcom.sun.management.jmxremote.rmi.port=5000 \
 -Dcom.sun.management.jmxremote.host=0.0.0.0 \
 -Djava.rmi.server.hostname=0.0.0.0"

COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_TOOL_OPTIONS -jar /opt/app.jar
