FROM openjdk:11
COPY target/masterPass-1.0.jar /home/java/masterPass-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "/home/java/masterPass-1.0.jar"]
