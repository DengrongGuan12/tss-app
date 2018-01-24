FROM 192.168.1.202/common/basejava
RUN mkdir /data
VOLUME /data
ADD ./target/tss-app-1.0-SNAPSHOT.jar app.jar
EXPOSE 12358
ENTRYPOINT ["java","-javaagent:/data/pp-agent/pinpoint-bootstrap-1.6.0.jar","-Dpinpoint.agentId=tss-100","-Dpinpoint.applicationName=tss","-jar","/app.jar"]