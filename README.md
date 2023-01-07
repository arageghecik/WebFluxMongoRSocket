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
```
db=mongo\
sudo systemctl status mongo

4.
