FROM java:7
ADD hibernate-docker.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT [ "java -Djava.security.egd=file:/dev/.urandom","-jar","/app.jar" ]