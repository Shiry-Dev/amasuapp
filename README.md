## Amasuapp - Backend
___
### Description:

___
### Swagger:
In your browser, paste the following URL:
```bash
http://localhost:8090/swagger-ui/index.html
```

___
#### How to run:
1. **Clone the repository**
```bash
https://github.com/Shiry-Dev/amasuapp
```

2. **Run the following command in the terminal to create the database:**
 
```bash
docker-compose up -d
```

3. **Run the following command in the terminal to start the application:**
```bash
mvn spring-boot:run
```

- 1. Alternatively, you can compile the application using the following command:
    ```bash
    mvn clean package -DskipTests
    ```

- 2. After compiling, you can run the application using the following command:
    ```bash
    java -jar target/*.jar
    ```
4. **To stop the application:**
```Ctrl + C```

5. **After testing the application, run the following command in the terminal to stop the database:**
```bash
docker-compose down
```
