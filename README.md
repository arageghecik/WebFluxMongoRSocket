# reactive

1.
CrudRepository to ReactiveCrudRepository

2.
```java
Iterable<Aircraft> to  Flux<Aircraft>
```

3.
add to maven (remove old db dependency and add new reactive)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-rsocket</artifactId>
</dependency>
```
db=mongo\
sudo systemctl status mongo

4.
ready, but we need testing with console by httpie (<code>snap install httpie</code>(for any os))\
testing command is <code>http -S :8080/acstream</code>

5.
for this version we don't have socket for communication frontend to backend like a wsocket version project

6. 
this app by  "...withoutRSocket" branch can get data from any app with  localhost:7635/aircraft endpoint\ 
but for RSocket branch we need get data from "planeFinder_with_WebFlux_R2DBC_Rsocket" app, they communicate with /acstream socket endpoint(port find yourself)