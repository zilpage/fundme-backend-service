FROM openjdk:oracle

COPY target/fundme-0.0.1-SNAPSHOT.jar app/fundme-0.0.1-SNAPSHOT.jar

EXPOSE 7080
CMD ["java","-Dspring.profiles.active=docker", "-jar", "app/fundme-0.0.1-SNAPSHOT.jar"]
