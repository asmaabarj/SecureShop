FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8086

# Ajouter wait-for-it script
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "/app/app.jar"]