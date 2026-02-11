
# 2) Etapa de runtime (imagem final mais leve)
FROM azul/zulu-openjdk-alpine:21

WORKDIR /app

# ajusta o nome do jar se necessário
COPY target/fhir-processor-1.0.0.jar /app/app.jar

# portas só se o FHIR Processor expuser HTTP (por exemplo 8090)
# EXPOSE 8090

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
