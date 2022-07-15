FROM eclipse-temurin:17.0.2_8-jre-alpine
ADD /target/tnakrean.jar tnakrean.jar
ENTRYPOINT ["java","-jar","tnakrean.jar"]

