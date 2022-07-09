From openjdk:17
ADD /target/tnakrean.jar tnakrean.jar
ENTRYPOINT ["java","-jar","tnakrean.jar"]

