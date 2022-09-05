while the spring boot application is running the API is fully documented at http://localhost:8080/swagger-ui
the swagger ui both fully documents the APIs and allows the user to actually try it out
note: there are currently a few bugs in swagger version 3 that will require filing bugs for that team to fix
note: this code will not process special log formats like circular logs

to start the spring application with the jar file from the jar directory, can fully specify path then run from anywhere:
    java -jar ./varlogs-1.0.0-SNAPSHOT.jar com.duchin.varlogs.VarLogsApplication

to start the spring application with maven from the pom.xml directory:
    mvn clear install
    mvn spring-boot:run

