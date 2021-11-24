FROM openjdk:11-slim
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
EXPOSE 80
ENTRYPOINT java -jar app.jar
