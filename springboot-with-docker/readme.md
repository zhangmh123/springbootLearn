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
#Linux添加加速器
#阿里云加速器(要到阿里云开发者平台注册账号获取加速器地址)
echo "DOCKER_OPTS=\"--registry-mirror=https://xxxx.mirror.aliyuncs.com\"" | sudo tee -a /etc/default/dockersudo
#网易加速器
echo "DOCKER_OPTS=\"--registry-mirror=http://hub-mirror.c.163.com\"" | sudo tee -a /etc/default/dockersudo

#配好cd到项目目录执行：
1.构建镜像
mvn package docker:build -DpushImage
...
Step 0 : FROM felizi/docker-alpine-oraclejdk8
Pulling from felizi/docker-alpine-oraclejdk8
654b494ae505: Pull complete 
86fcf91a9125: Pull complete 
8c25202c7dd4: Pull complete 
d5744ce954f1: Pull complete 
e4586c76c95a: Pull complete 
Digest: sha256:3d3baa2abe17420e9603a91c499b54c99c2405271107648334ed86cb4b95c465
Status: Downloaded newer image for felizi/docker-alpine-oraclejdk8:latest
 ---> e4586c76c95a
Step 1 : VOLUME /tmp
 ---> Running in f6a0404a0b1a
 ---> 1114d55c3925
Removing intermediate container f6a0404a0b1a
Step 2 : ADD gs-spring-boot-docker-0.1.0.jar app.jar
 ---> 26ed80800307
Removing intermediate container d022206c7423
Step 3 : RUN sh -c 'touch /app.jar'
 ---> Running in f78512e9d256
 ---> 1360ab41af27
Removing intermediate container f78512e9d256
Step 4 : ENV JAVA_OPTS ""
 ---> Running in 8f0a06e58025
 ---> 42495b2251f9
Removing intermediate container 8f0a06e58025
Step 5 : ENTRYPOINT sh -c java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
 ---> Running in 2f8072c9392d
 ---> 4af2a6ede069
Removing intermediate container 2f8072c9392d
Successfully built 4af2a6ede069
2.查看刚才构建的镜像
[root@vmr1705111502 ~]# docker images
REPOSITORY                        TAG                 IMAGE ID            CREATED              VIRTUAL SIZE
springio/gs-spring-boot-docker    latest              4af2a6ede069        About a minute ago   195.7 MB
felizi/docker-alpine-oraclejdk8   latest              e4586c76c95a        12 months ago        166.8 MB
3.运行镜像
[root@vmr1705111502 ~]# docker run -p 8080:8080 -t springio/gs-spring-boot-docker

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.5.3.RELEASE)

2017-07-05 06:27:37.742  INFO 5 --- [           main] hello.Application                        : Starting Application v0.1.0 on 893f9798bf9d with PID 5 (/app.jar started by root in /)
2017-07-05 06:27:37.763  INFO 5 --- [           main] hello.Application                        : No active profile set, falling back to default profiles: default
2017-07-05 06:27:37.946  INFO 5 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@48533e64: startup date [Wed Jul 05 06:27:37 GMT 2017]; root of context hierarchy
2017-07-05 06:27:41.515  INFO 5 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat initialized with port(s): 8080 (http)
2017-07-05 06:27:41.561  INFO 5 --- [           main] o.apache.catalina.core.StandardService   : Starting service Tomcat
2017-07-05 06:27:41.564  INFO 5 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/8.5.14
2017-07-05 06:27:41.803  INFO 5 --- [ost-startStop-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2017-07-05 06:27:41.804  INFO 5 --- [ost-startStop-1] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3867 ms
2017-07-05 06:27:42.263  INFO 5 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Mapping servlet: 'dispatcherServlet' to [/]
2017-07-05 06:27:42.276  INFO 5 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2017-07-05 06:27:42.277  INFO 5 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2017-07-05 06:27:42.278  INFO 5 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'httpPutFormContentFilter' to: [/*]
2017-07-05 06:27:42.278  INFO 5 --- [ost-startStop-1] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2017-07-05 06:27:43.043  INFO 5 --- [           main] s.w.s.m.m.a.RequestMappingHandlerAdapter : Looking for @ControllerAdvice: org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@48533e64: startup date [Wed Jul 05 06:27:37 GMT 2017]; root of context hierarchy
2017-07-05 06:27:43.206  INFO 5 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/]}" onto public java.lang.String hello.Application.home()
2017-07-05 06:27:43.216  INFO 5 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
2017-07-05 06:27:43.217  INFO 5 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
2017-07-05 06:27:43.269  INFO 5 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-07-05 06:27:43.270  INFO 5 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-07-05 06:27:43.349  INFO 5 --- [           main] o.s.w.s.handler.SimpleUrlHandlerMapping  : Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
2017-07-05 06:27:43.672  INFO 5 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2017-07-05 06:27:43.844  INFO 5 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2017-07-05 06:27:43.863  INFO 5 --- [           main] hello.Application                        : Started Application in 7.357 seconds (JVM running for 8.606)
4 查看运行的镜像
[root@vmr1705111502 ~]# docker ps -l
CONTAINER ID        IMAGE                            COMMAND                CREATED             STATUS              PORTS                    NAMES
893f9798bf9d        springio/gs-spring-boot-docker   "sh -c 'java $JAVA_O   8 minutes ago       Up 8 minutes        0.0.0.0:8080->8080/tcp   serene_shockley   



