From openjdk:17
EXPOSE 8080
CMD mkdir resources && cd resources && mkdir images
ADD tnakrean.jar tnakrean.jar
ENTRYPOINT ["java","-jar","tnakrean.jar"]
