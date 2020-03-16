FROM openjdk:13

ADD target/workout_tracker_api-2.2.1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

ARG db_name=mysql://host.docker.internal:3306/workout_tracker
ARG db_user
ARG db_secret

ENV WORKOUT_DB_NAME=$db_name
ENV WORKOUT_DB_USER=$db_user
ENV WORKOUT_DB_SECRET_KEY=$db_secret

EXPOSE 8080

