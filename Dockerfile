From openjdk:17
EXPOSE 8080
ADD tnakrean.jar tnakrean.jar
ENTRYPOINT ["java","-jar","tnakrean.jar"]
