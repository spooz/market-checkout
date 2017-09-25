# Market Checkout Component

### Technical overview
 - Java 8
 - Spring 4, Spring Boot
 - MongoDB -> embeded
 - Testing: Spock, AssertJ
 - Gradle, Docker

### Build 
```
gradle build
```

### Test
```
gradle test
```

### Docker build
```
gradle buildDocker
```

### API
Swagger API docs are enabled. UI version at :8080/swagger-ui.html

```
POST /api/v1/checkout/item - checkout item with given **id** and **quantity**
POST /api/v1/checkout - checkout items, returns payment report
```

### Design overview
The design is centered around **Item** and **ItemPromotion** entities which store data needed to verify and calculate payments.
Obvious reason to store it in database is the ease of manipulation. Application logic is devided into two main interfaces:
- **ItemStorage**  - responsible for storing checked out items betweens requests - should be thread safe. **ItemStorageInMemory** uses
CuncurrentHashMap to deal with synchronization problem.
- **ItemPriceCalculator** - responsible for calculating the final price of items and the final promotion with stored given data. **ItemPriceCalculatorDefault** is a naive implementation
without much optimalization

Other components:
- **ItemFacade** is an external API which should be used by other clients
- **ItemConfig** is a centralized Spring Bean config, instead of class annotation - makes it easier to review and debug


Unit tests cover hard logic: storage and price calculation. Classes are tested separately but could be as well by tested through the facade.


### Things to improve
- item data endpoints
- unit, integration, e2e tests gradle task separation
- use of real mongodb server
- docker-compose file to make deployment easier
- instead of in memory storage, usege of standalone cache (Redis?)
- cachable item repositories