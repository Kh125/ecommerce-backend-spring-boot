FROM openjdk:17-jdk-alpine
EXPOSE 9090
ADD target/spring-boot-ecommerce.jar spring-boot-ecommerce.jar
ENTRYPOINT ["java", "-jar", "spring-boot-ecommerce.jar"]
