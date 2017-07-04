#参考地址
https://spring.io/guides/gs/spring-boot-docker/
#原文Dockerfile中的基础镜像在阿里云上找不到，换为felizi/docker-alpine-oraclejdk8
FROM felizi/docker-alpine-oraclejdk8
VOLUME /tmp
ADD gs-spring-boot-docker-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
#国内镜像库建议用阿里云加速器,参考地址：
http://blog.csdn.net/qq_30259339/article/details/52400673

