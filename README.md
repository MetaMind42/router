# Router

Root domain to Telegram channel redirection service with custom URL shortener at jakubpejzl.me/*.

## Stack

-   Java 21
-   Spring Boot 3.4.5
-   Spring Data JDBC
-   Oracle ATP DB (23ai)

## Deployment

Fill in Oracle ATP DB credentials in application.properties first, please.

```bash
mvn clean install
java -jar target/router-23.05.jar
```

## License

I released this project under [MIT License](https://opensource.org/license/mit). Use freely for any purpose. Copy, modify, distribute or commercialize without permission. Provided the copyright notice and permission notice are included in all copies or substantial portions of the file.
