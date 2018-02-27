# spring-kotlin-jdbi
playground for spring-boot, kotlin , jdbi

## status

 - under development
 
## build and run playground (docker)
```
    $ make -C rest-api app.compose.up

```

- browse to: http://localhost:8080/swagger-ui.html


## build

```
    $ make -C rest-api app.build

```

## run local db (docker)

```
    $ make -C rest-api db.local.up

```

## run from Intellij

    VM options: -Dspring.profiles.active=local
    Environment variables: APP_DB_URL=localhost:5432/app
    
  ```
      # make sure, you have our db running on that port, e.g.:
      $ make -C rest-api db.local.up
  
  ```  

## This example project is based on ...
- https://github.com/making/spring-boot-db-samples
- https://www.sitepoint.com/combining-spring-boot-and-jdbi/
- https://github.com/dhagge/spring-boot-jdbi-seed
- https://github.com/oldpatricka/spring-boot-jdbi

## Whats wrong with orm, jpa, hibernate and in-memory h2-db these days ?

There is no silver bullet. 
It's born in a world of single-instance big fat application servers.
It hardly fits into a modern world of:

- functional programming: e.g. immutable threadsafe pojos / data classes 
- CQRS and eventsourcing
- horizontal scaling of polyglot microservices

Make up your mind ...

- How hibernate ruined Monica's career: https://www.toptal.com/java/how-hibernate-ruined-my-career
- Why do I hate hibernate: https://de.slideshare.net/alimenkou/why-do-i-hate-hibernate-12998784
- ORM is an antipattern: http://seldo.com/weblog/2011/08/11/orm_is_an_antipattern
- Opinionated JPA: https://leanpub.com/opinionatedjpa/read
- Lightweight ORM, do it yourself: https://blog.philipphauer.de/do-it-yourself-orm-alternative-hibernate-drawbacks/
- Don't use H2 db for testing, use docker: https://blog.philipphauer.de/dont-use-in-memory-databases-tests-h2/

- https://www.sitepoint.com/combining-spring-boot-and-jdbi/

